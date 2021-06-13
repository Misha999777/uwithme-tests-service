package tk.tcomad.testsystem.endpoint;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import tk.tcomad.testsystem.exception.BadRequestException;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.api.TestApi;
import tk.tcomad.testsystem.model.mapper.TestMapper;
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
    @NonNull
    private final TestMapper testMapper;

    @GetMapping
    public List<Test> getTests() {
        return testRepository.findAllByAuthorId(getUserId());
    }

    @GetMapping("/{testId}")
    public TestApi getTest(@PathVariable String testId) {
        return testRepository.findByAuthorIdAndId(getUserId(), testId)
                .map(testMapper::toTestApi)
                .orElseThrow(() -> new NotFoundException("Test not found"));
    }

    @PostMapping
    public TestApi saveTest(@RequestBody TestApi testApi) {
        if (Objects.nonNull(testApi.getId())) {
            throw new BadRequestException("Use PUT for update");
        }

        Test testToSave = testMapper.toTestDb(testApi).toBuilder()
                .authorId(getUserId())
                .questions(Set.of())
                .testSessions(Set.of())
                .build();

        Test savedTest = testRepository.save(testToSave);

        return testMapper.toTestApi(savedTest);
    }

    @PutMapping("/{testId}")
    public TestApi updateTest(@PathVariable String testId, @RequestBody @NonNull TestApi testApi) {
        Test savedTestDb = Optional.ofNullable(testId)
                .map(testRepository::findById)
                .orElseThrow(() -> new BadRequestException("Use POST for save"))
                .orElseThrow(() -> new NotFoundException("Test not found"));

        Test testToSave = savedTestDb.toBuilder()
                .name(testApi.getName())
                .durationMinutes(testApi.getDurationMinutes())
                .questionsNumber(testApi.getQuestionsNumber())
                .build();

        Test savedTest = testRepository.save(testToSave);

        return testMapper.toTestApi(savedTest);
    }

    @DeleteMapping("/{testId}")
    public void deleteTest(@PathVariable String testId) {
        testRepository.deleteByAuthorIdAndId(getUserId(), testId);
    }
}
