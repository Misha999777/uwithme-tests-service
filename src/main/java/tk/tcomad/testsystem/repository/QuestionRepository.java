package tk.tcomad.testsystem.repository;

import org.springframework.data.repository.CrudRepository;
import tk.tcomad.testsystem.model.persistence.QuestionDb;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<QuestionDb, Long> {

    List<QuestionDb> findAllByTestIdOrderById(String testId);

    Optional<QuestionDb> findByTestIdAndId(String testId, Long questionId);

    void deleteByTestIdAndId(String testId, Long questionId);
}
