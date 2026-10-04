package education.uwithme.testsystem.endpoint;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import education.uwithme.testsystem.builder.TestSessionBuilder;
import education.uwithme.testsystem.exception.BadRequestException;
import education.uwithme.testsystem.exception.NotFoundException;
import education.uwithme.testsystem.model.domain.TestSession;
import education.uwithme.testsystem.model.mapper.TestSessionMapper;
import education.uwithme.testsystem.model.persistence.TestSessionDb;
import education.uwithme.testsystem.repository.TestSessionRepository;
import education.uwithme.testsystem.service.TestSessionService;

import static education.uwithme.testsystem.security.SecurityContextUtils.getUserId;

import java.util.Objects;

@RestController
@RequestMapping("/tests/{testId}/sessions")
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

    @GetMapping
    public TestSession generateStudentSession(@PathVariable String testId) {
        TestSessionDb testSession = testSessionRepository.findByUserIdAndTestId(getUserId(), testId)
                .orElseGet(() -> {
                    TestSessionDb toSave = testSessionMapper.toDb(testSessionBuilder.withTestId(testId).build());
                    return testSessionRepository.save(toSave);
                });

        TestSession saved = testSessionMapper.toDomain(testSession);
        testSessionService.removeCorrectAnswers(saved);

        return saved;
    }

    @PutMapping
    public TestSession saveStudentSession(@PathVariable String testId, @RequestBody TestSession testSessionApi) {
        TestSession testSession = testSessionRepository.findByTestIdAndId(testId, testSessionApi.getId())
                .map(testSessionMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Session not found"));

        if (!Objects.equals(testSession.getUserId(), getUserId())) {
            throw new BadRequestException("Not an author");
        }

        if (Objects.nonNull(testSession.getElapsedTime())) {
            throw new BadRequestException("Session already saved");
        }

        testSessionService.saveTestSession(testSession, testSessionApi);

        return testSessionApi;
    }
}
