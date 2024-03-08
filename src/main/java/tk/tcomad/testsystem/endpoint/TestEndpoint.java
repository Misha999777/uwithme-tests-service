package tk.tcomad.testsystem.endpoint;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.domain.Test;
import tk.tcomad.testsystem.model.mapper.TestMapper;
import tk.tcomad.testsystem.model.persistence.TestDb;
import tk.tcomad.testsystem.repository.TestRepository;

import java.util.List;
import java.util.stream.Collectors;

import static tk.tcomad.testsystem.security.SecurityContextUtils.getUserId;

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
    public List<Test> getTests() {
        return testRepository.findAllByAuthorIdOrderByCreateDateDesc(getUserId())
                .stream().map(testMapper::toDomain)
                .collect(Collectors.toList());
    }

    @GetMapping("/{testId}")
    public Test getTest(@PathVariable String testId) {
        return testRepository.findByAuthorIdAndId(getUserId(), testId)
                .map(testMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Test not found"));
    }

    @PostMapping
    public Test createTest(@RequestBody Test testApi) {
        testApi.setAuthorId(getUserId());

        TestDb savedTest = testRepository.save(testMapper.toDb(testApi));

        return testMapper.toDomain(savedTest);
    }

    @DeleteMapping("/{testId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteTest(@PathVariable String testId) {
        testRepository.deleteByAuthorIdAndId(getUserId(), testId);
    }
}
