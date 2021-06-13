package tk.tcomad.testsystem.builder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tk.tcomad.testsystem.model.persistence.Question;
import tk.tcomad.testsystem.model.persistence.TestSession;
import tk.tcomad.testsystem.provider.QuestionProvider;
import tk.tcomad.testsystem.security.UserContextHolder;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TestSessionBuilder {

    @NonNull
    private final QuestionProvider questionProvider;

    private String testId;

    public TestSessionBuilder withTestId(String testId) {
        this.testId = testId;
        return this;
    }

    public TestSession build() {
        Set<Question> questions = questionProvider.getQuestionsByTestId(testId);
        return TestSession.builder()
                .testId(testId)
                .userId(UserContextHolder.getUserId())
                .startTime(Instant.now())
                .questions(questions)
                .build();
    }
}
