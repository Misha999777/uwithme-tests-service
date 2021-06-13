package tk.tcomad.testsystem.provider;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.mapper.QuestionMapper;
import tk.tcomad.testsystem.model.persistence.Answer;
import tk.tcomad.testsystem.model.persistence.Question;
import tk.tcomad.testsystem.model.persistence.Test;
import tk.tcomad.testsystem.repository.TestRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionProvider {

    @NonNull
    private final TestRepository testRepository;
    @NonNull
    private final QuestionMapper questionMapper;

    public Set<Question> getQuestionsByTestId(String testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test not found"));

        List<Question> questions = test.getQuestions().stream()
                .map(this::buildQuestion)
                .collect(Collectors.toList());
        Collections.shuffle(questions);

        return questions.stream().limit(test.getQuestionsNumber()).collect(Collectors.toSet());
    }

    private Question buildQuestion(Question question) {
        List<Answer> answers = question.getAnswers().stream()
                .peek(answer -> answer.setCorrect(false))
                .collect(Collectors.toList());

        return question.toBuilder()
                .answers(answers)
                .build();
    }
}
