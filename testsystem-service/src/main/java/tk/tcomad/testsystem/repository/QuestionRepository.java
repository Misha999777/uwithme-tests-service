package tk.tcomad.testsystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import tk.tcomad.testsystem.model.persistence.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findAllByTestId(String testId);
}
