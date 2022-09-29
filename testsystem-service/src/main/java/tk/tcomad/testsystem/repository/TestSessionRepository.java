package tk.tcomad.testsystem.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import tk.tcomad.testsystem.model.persistence.TestSessionDb;

public interface TestSessionRepository extends CrudRepository<TestSessionDb, Long> {

    Optional<TestSessionDb> findByUserIdAndTestId(String userId, String testId);
    Optional<TestSessionDb> findByTestIdAndId(String testId, Long testSessionId);
    void deleteByTestIdAndId(String testId, Long testSessionId);
}
