package tk.tcomad.testsystem.endpoint;

import static tk.tcomad.testsystem.security.UserContextHolder.getUserId;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tk.tcomad.testsystem.exception.BadRequestException;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.domain.Test;
import tk.tcomad.testsystem.model.mapper.TestMapper;
import tk.tcomad.testsystem.model.persistence.TestDb;
import tk.tcomad.testsystem.repository.TestRepository;

@RestController
@Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
@RequestMapping("tests")
@RequiredArgsConstructor
public class TestEndpoint {

    @NonNull
    private final TestRepository testRepository;
    @NonNull
    private final TestMapper testMapper;

    @GetMapping
    public List<TestDb> getTests() {
        return testRepository.findAllByAuthorId(getUserId());
    }

    @GetMapping("/{testId}")
    public Test getTest(@PathVariable String testId) {
        return testRepository.findByAuthorIdAndId(getUserId(), testId)
                             .map(testMapper::toTestApi)
                             .orElseThrow(() -> new NotFoundException("Test not found"));
    }

    @PostMapping
    public Test saveTest(@RequestBody Test testApi) {
        if (Objects.nonNull(testApi.getId())) {
            throw new BadRequestException("Use PUT for update");
        }

        TestDb testToSave = testMapper.toTestDb(testApi).toBuilder()
                                      .authorId(getUserId())
                                      .questions(List.of())
                                      .testSessions(List.of())
                                      .build();

        TestDb savedTest = testRepository.save(testToSave);

        return testMapper.toTestApi(savedTest);
    }

    @PutMapping("/{testId}")
    public Test updateTest(@PathVariable String testId, @RequestBody @NonNull Test testApi) {
        TestDb savedTestDb = Optional.ofNullable(testId)
                                     .map(testRepository::findById)
                                     .orElseThrow(() -> new BadRequestException("Use POST for save"))
                                     .orElseThrow(() -> new NotFoundException("Test not found"));

        TestDb testToSave = savedTestDb.toBuilder()
                                       .name(testApi.getName())
                                       .durationMinutes(testApi.getDurationMinutes())
                                       .questionsNumber(testApi.getQuestionsNumber())
                                       .build();

        TestDb savedTest = testRepository.save(testToSave);

        return testMapper.toTestApi(savedTest);
    }

    @DeleteMapping("/{testId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteTest(@PathVariable String testId) {
        testRepository.deleteByAuthorIdAndId(getUserId(), testId);
    }
}
