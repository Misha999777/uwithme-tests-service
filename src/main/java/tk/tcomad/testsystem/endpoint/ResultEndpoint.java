package tk.tcomad.testsystem.endpoint;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.domain.TestSession;
import tk.tcomad.testsystem.model.mapper.TestSessionMapper;
import tk.tcomad.testsystem.model.persistence.TestSessionDb;
import tk.tcomad.testsystem.repository.TestSessionRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
@RequestMapping("tests/{testId}/results")
@RequiredArgsConstructor
public class ResultEndpoint {

    @NonNull
    private final TestSessionRepository testSessionRepository;
    @NonNull
    private final TestSessionMapper testSessionMapper;

    @GetMapping
    public List<TestSession> getResults(@PathVariable String testId) {
        return testSessionRepository.findAllByTestIdOrderById(testId)
                .stream()
                .map(testSessionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @GetMapping("/{resultId}")
    public TestSession getResult(@PathVariable String testId, @PathVariable Long resultId) {
        TestSessionDb testSession = testSessionRepository.findByTestIdAndId(testId, resultId)
                .orElseThrow(() -> new NotFoundException("Session not found"));

        return testSessionMapper.toDomain(testSession);
    }

    @DeleteMapping("/{resultId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteResult(@PathVariable String testId, @PathVariable Long resultId) {
        testSessionRepository.deleteByTestIdAndId(testId, resultId);
    }
}
