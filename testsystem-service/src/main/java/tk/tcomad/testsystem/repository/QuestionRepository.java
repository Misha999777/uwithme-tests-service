package tk.tcomad.testsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import tk.tcomad.testsystem.model.persistence.QuestionDb;

public interface QuestionRepository extends CrudRepository<QuestionDb, Long> {

    List<QuestionDb> findAllByTestId(String testId);
    Optional<QuestionDb> findByTestIdAndId(String testId, Long questionId);
    void deleteByTestIdAndId(String testId, Long questionId);
}
