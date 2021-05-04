package tk.tcomad.testsystem.repository;

import org.springframework.data.repository.CrudRepository;
import tk.tcomad.testsystem.model.persistence.Question;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAllByTestId(String testId);
}
