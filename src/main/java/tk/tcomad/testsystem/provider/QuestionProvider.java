package tk.tcomad.testsystem.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.persistence.Question;
import tk.tcomad.testsystem.model.persistence.Test;
import tk.tcomad.testsystem.repository.TestRepository;

@Service
@RequiredArgsConstructor
public class QuestionProvider {

    @NonNull
    private final TestRepository testRepository;

    public Set<Question> getQuestionsByTestId(String testId) {
        Test test = testRepository.findById(testId)
                                  .orElseThrow(() -> new NotFoundException("Test not found"));

        List<Question> questions = new ArrayList<>(test.getQuestions());
        Collections.shuffle(questions);

        return questions.stream().limit(test.getQuestionsNumber()).collect(Collectors.toSet());
    }
}
