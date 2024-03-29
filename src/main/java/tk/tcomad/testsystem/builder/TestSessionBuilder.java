package tk.tcomad.testsystem.builder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.tcomad.testsystem.exception.BadRequestException;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.domain.Question;
import tk.tcomad.testsystem.model.domain.Test;
import tk.tcomad.testsystem.model.domain.TestSession;
import tk.tcomad.testsystem.model.mapper.TestMapper;
import tk.tcomad.testsystem.provider.QuestionProvider;
import tk.tcomad.testsystem.repository.TestRepository;

import java.time.Instant;
import java.util.Set;

import static tk.tcomad.testsystem.security.SecurityContextUtils.getUserId;

@Component
@RequiredArgsConstructor
public class TestSessionBuilder {

    @NonNull
    private final TestRepository testRepository;
    @NonNull
    private final TestMapper testMapper;
    @NonNull
    private final QuestionProvider questionProvider;

    private String testId;

    public TestSessionBuilder withTestId(String testId) {
        this.testId = testId;
        return this;
    }

    public TestSession build() {
        Test test = testRepository.findById(testId)
                .map(testMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Test not found"));
        Set<Question> questions = questionProvider.getQuestionsByTestId(testId);

        if (questions.isEmpty()) {
            throw new BadRequestException("Test does not have any questions");
        }

        return TestSession.builder()
                .testId(testId)
                .userId(getUserId())
                .startTime(Instant.now())
                .questionSnapshots(questions)
                .durationMinutes(test.getDurationMinutes())
                .build();
    }
}
