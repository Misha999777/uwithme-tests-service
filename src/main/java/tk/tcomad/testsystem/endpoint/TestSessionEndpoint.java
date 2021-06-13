package tk.tcomad.testsystem.endpoint;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import tk.tcomad.testsystem.builder.TestSessionBuilder;
import tk.tcomad.testsystem.exception.BadRequestException;
import tk.tcomad.testsystem.model.api.TestSessionApi;
import tk.tcomad.testsystem.model.mapper.TestSessionMapper;
import tk.tcomad.testsystem.model.persistence.TestSession;
import tk.tcomad.testsystem.repository.TestSessionRepository;
import tk.tcomad.testsystem.security.UserContextHolder;
import tk.tcomad.testsystem.service.TestSessionService;

import java.util.Objects;

@RestController
@Secured("ROLE_STUDENT")
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

    @GetMapping("/{sessionId}")
    public TestSessionApi getSession(@PathVariable Long sessionId) {
        TestSession testSession = testSessionRepository.findById(sessionId).orElseThrow();

        return testSessionMapper.toTestSessionApi(testSession);
    }

    @PostMapping
    public TestSessionApi beginTest(@PathVariable String testId) {
        TestSession testSession = testSessionBuilder.withTestId(testId).build();

        testSessionRepository.save(testSession);

        return testSessionMapper.toTestSessionApi(testSession);
    }

    @PutMapping
    public TestSessionApi endTest(@RequestBody @NonNull TestSessionApi testSessionApi) {
        TestSession testSession = testSessionRepository.findById(testSessionApi.getId())
                .orElseThrow(() -> new BadRequestException(""));

        if(!Objects.equals(testSession.getUserId(), UserContextHolder.getUserId())) {
            throw new BadRequestException("");
        }

        testSessionService.saveTestSession(testSession, testSessionApi);

        return testSessionApi;
    }
}
