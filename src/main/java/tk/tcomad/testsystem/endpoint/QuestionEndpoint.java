package tk.tcomad.testsystem.endpoint;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.mapper.QuestionMapper;
import tk.tcomad.testsystem.model.persistence.Question;
import tk.tcomad.testsystem.model.api.QuestionApi;
import tk.tcomad.testsystem.repository.QuestionRepository;
import tk.tcomad.testsystem.repository.TestRepository;

import java.util.List;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("questions")
@RequiredArgsConstructor
public class QuestionEndpoint {

    @NonNull
    private final QuestionRepository questionRepository;
    @NonNull
    private final TestRepository testRepository;
    @NonNull
    private final QuestionMapper questionMapper;

    @GetMapping
    public List<Question> getTestQuestions(@RequestParam String testId) {
        return questionRepository.findAllByTestId(testId);
    }

    @GetMapping("/{questionId}")
    public Question getQuestion(@PathVariable Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question not found"));
    }

    @DeleteMapping("/{questionId}")
    public void deleteQuestion(@PathVariable Long questionId) {
        questionRepository.deleteById(questionId);
    }

    @PostMapping
    public QuestionApi saveQuestion(@RequestBody QuestionApi question) {
        if (!testRepository.existsById(question.getTestId())) {
            throw new NotFoundException("Test not found");
        }

        Question toSave = questionMapper.toQuestion(question);
        Question saved = questionRepository.save(toSave);

        return questionMapper.toQuestionApi(saved);
    }
}
