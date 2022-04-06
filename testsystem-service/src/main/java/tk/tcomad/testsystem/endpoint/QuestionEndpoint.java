package tk.tcomad.testsystem.endpoint;

import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tk.tcomad.testsystem.exception.NotFoundException;
import tk.tcomad.testsystem.model.api.QuestionApi;
import tk.tcomad.testsystem.model.mapper.QuestionMapper;
import tk.tcomad.testsystem.model.persistence.Question;
import tk.tcomad.testsystem.repository.QuestionRepository;
import tk.tcomad.testsystem.repository.TestRepository;

@RestController
@Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
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
    public List<QuestionApi> getTestQuestions(@RequestParam String testId) {
        return questionRepository.findAllByTestId(testId).stream()
                                 .map(questionMapper::toQuestionApi)
                                 .collect(Collectors.toList());
    }

    @GetMapping("/{questionId}")
    public QuestionApi getQuestion(@PathVariable Long questionId) {
        return questionRepository.findById(questionId)
                                 .map(questionMapper::toQuestionApi)
                                 .orElseThrow(() -> new NotFoundException("Question not found"));
    }

    @PostMapping
    public QuestionApi saveQuestion(@RequestBody QuestionApi question) {
        if (!testRepository.existsById(question.getTestId())) {
            throw new NotFoundException("Test not found");
        }

        Question toSave = questionMapper.toQuestionDb(question);
        Question saved = questionRepository.save(toSave);

        return questionMapper.toQuestionApi(saved);
    }

    @DeleteMapping("/{questionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable Long questionId) {
        questionRepository.deleteById(questionId);
    }
}
