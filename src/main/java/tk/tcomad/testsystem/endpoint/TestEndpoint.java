package tk.tcomad.testsystem.endpoint;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import tk.tcomad.testsystem.exception.BadRequestException;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.persistence.Test;
import tk.tcomad.testsystem.repository.TestRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static tk.tcomad.testsystem.security.UserContextHolder.getUserId;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("test")
@RequiredArgsConstructor
public class TestEndpoint {

    @NonNull
    private final TestRepository testRepository;

    @GetMapping
    public List<Test> getTests() {
        return testRepository.findAllByAuthorId(getUserId());
    }

    @GetMapping("/{testId}")
    public Test getTest(@PathVariable String testId) {
        return testRepository.findByAuthorIdAndId(getUserId(), testId)
                .orElseThrow(() -> new NotFoundException("Test not found"));
    }

    @PostMapping
    public Test saveTest(@RequestBody Test test) {
        if (Objects.nonNull(test.getId())) {
            throw new BadRequestException("Use PUT for update");
        }

        Test testToSave = test.toBuilder()
                .authorId(getUserId())
                .questions(Set.of())
//                .testSessions(Set.of())
                .build();

        return testRepository.save(testToSave);
    }

    @PutMapping("/{testId}")
    public Test updateTest(@PathVariable String testId, @RequestBody @NonNull Test test) {
        Test savedTest = Optional.ofNullable(testId)
                .map(testRepository::findById)
                .orElseThrow(() -> new BadRequestException("Use POST for save"))
                .orElseThrow(() -> new NotFoundException("Test not found"));

        Test testToSave = savedTest.toBuilder()
                .name(test.getName())
                .durationMinutes(test.getDurationMinutes())
                .questionsNumber(test.getQuestionsNumber())
                .build();

        return testRepository.save(testToSave);
    }

    @DeleteMapping("/{testId}")
    public void deleteTest(@PathVariable String testId) {
        testRepository.deleteByAuthorIdAndId(getUserId(), testId);
    }
}
