package education.uwithme.testsystem.provider;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import education.uwithme.testsystem.exception.NotFoundException;
import education.uwithme.testsystem.model.domain.Question;
import education.uwithme.testsystem.model.domain.Test;
import education.uwithme.testsystem.model.mapper.QuestionMapper;
import education.uwithme.testsystem.model.mapper.TestMapper;
import education.uwithme.testsystem.repository.QuestionRepository;
import education.uwithme.testsystem.repository.TestRepository;

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

        List<Question> questions = questionRepository.findAllByTestIdOrderById(testId)
                .stream()
                .map(questionMapper::toDomain)
                .collect(Collectors.toList());

        Collections.shuffle(questions);

        return questions.stream()
                .limit(test.getQuestionsNumber())
                .collect(Collectors.toSet());
    }
}
