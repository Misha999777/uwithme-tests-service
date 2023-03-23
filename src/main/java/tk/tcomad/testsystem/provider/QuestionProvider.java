package tk.tcomad.testsystem.provider;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.domain.Question;
import tk.tcomad.testsystem.model.domain.Test;
import tk.tcomad.testsystem.model.mapper.QuestionMapper;
import tk.tcomad.testsystem.model.mapper.TestMapper;
import tk.tcomad.testsystem.repository.QuestionRepository;
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
    private final TestMapper testMapper;
    @NonNull
    private final QuestionRepository questionRepository;
    @NonNull
    private final QuestionMapper questionMapper;

    public Set<Question> getQuestionsByTestId(String testId) {
        Test test = testRepository.findById(testId)
                .map(testMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Test not found"));

        List<Question> questions = questionRepository.findAllByTestId(testId)
                .stream()
                .map(questionMapper::toDomain)
                .collect(Collectors.toList());

        Collections.shuffle(questions);

        return questions.stream()
                .limit(test.getQuestionsNumber())
                .collect(Collectors.toSet());
    }
}
