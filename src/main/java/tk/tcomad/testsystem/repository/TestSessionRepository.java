package tk.tcomad.testsystem.repository;

import org.springframework.data.repository.CrudRepository;
import tk.tcomad.testsystem.model.persistence.TestSessionDb;

import java.util.List;
import java.util.Optional;

public interface TestSessionRepository extends CrudRepository<TestSessionDb, Long> {

    List<TestSessionDb> findAllByTestIdOrderById(String testId);

    Optional<TestSessionDb> findByUserIdAndTestId(String userId, String testId);

    Optional<TestSessionDb> findByTestIdAndId(String testId, Long testSessionId);

    void deleteByTestIdAndId(String testId, Long testSessionId);
}
