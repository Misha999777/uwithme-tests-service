package education.uwithme.testsystem.builder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import education.uwithme.testsystem.exception.BadRequestException;
import education.uwithme.testsystem.exception.NotFoundException;
import education.uwithme.testsystem.model.domain.Question;
import education.uwithme.testsystem.model.domain.Test;
import education.uwithme.testsystem.model.domain.TestSession;
import education.uwithme.testsystem.model.mapper.TestMapper;
import education.uwithme.testsystem.provider.QuestionProvider;
import education.uwithme.testsystem.repository.TestRepository;

import static education.uwithme.testsystem.security.SecurityContextUtils.getUserId;

import java.time.Instant;
import java.util.Set;

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
