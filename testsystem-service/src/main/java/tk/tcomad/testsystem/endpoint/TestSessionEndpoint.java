package tk.tcomad.testsystem.endpoint;

import java.util.Objects;

import javax.transaction.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tk.tcomad.testsystem.builder.TestSessionBuilder;
import tk.tcomad.testsystem.exception.BadRequestException;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.domain.TestSession;
import tk.tcomad.testsystem.model.mapper.TestSessionMapper;
import tk.tcomad.testsystem.model.persistence.TestSessionDb;
import tk.tcomad.testsystem.repository.TestSessionRepository;
import tk.tcomad.testsystem.security.UserContextHolder;
import tk.tcomad.testsystem.service.TestSessionService;

@RestController
@RequestMapping("/test/{testId}/session")
@RequiredArgsConstructor
public class TestSessionEndpoint {

    @NonNull
    private final TestSessionRepository testSessionRepository;
    @NonNull
    private final TestSessionService testSessionService;
    @NonNull
    private final TestSessionBuilder testSessionBuilder;
    @NonNull
    private final TestSessionMapper testSessionMapper;

    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    @GetMapping("/{sessionId}")
    public TestSession getSession(@PathVariable String testId, @PathVariable Long sessionId) {
        TestSessionDb testSession = testSessionRepository.findByTestIdAndId(testId, sessionId)
                                                         .orElseThrow(() -> new NotFoundException("Session not found"));

        return testSessionMapper.toDomain(testSession);
    }

    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    @DeleteMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteSession(@PathVariable String testId, @PathVariable Long sessionId) {
        testSessionRepository.deleteByTestIdAndId(testId, sessionId);
    }

    @PostMapping
    public TestSession beginTest(@PathVariable String testId) {
        testSessionRepository.findByUserIdAndTestId(UserContextHolder.getUserId(), testId)
                             .ifPresent((ts) -> {
                                 throw new BadRequestException("Already done");
                             });

        TestSessionDb toSave = testSessionMapper.toDb(testSessionBuilder.withTestId(testId)
                                                                        .build());
        TestSession saved = testSessionMapper.toDomain(testSessionRepository.save(toSave));

        testSessionService.removeCorrectAnswers(saved);

        return saved;
    }

    @PutMapping
    public TestSession endTest(@PathVariable String testId, @RequestBody TestSession testSessionApi) {
        TestSession testSession = testSessionRepository.findByTestIdAndId(testId, testSessionApi.getId())
                                                       .map(testSessionMapper::toDomain)
                                                       .orElseThrow(() -> new NotFoundException("Session not found"));

        if (!Objects.equals(testSession.getUserId(), UserContextHolder.getUserId())) {
            throw new BadRequestException("Not an author");
        }

        testSessionService.saveTestSession(testSession, testSessionApi);

        return testSessionApi;
    }
}
