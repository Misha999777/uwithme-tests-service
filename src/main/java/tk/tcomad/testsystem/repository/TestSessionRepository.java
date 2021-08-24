package tk.tcomad.testsystem.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import tk.tcomad.testsystem.model.persistence.TestSession;

public interface TestSessionRepository extends CrudRepository<TestSession, Long> {

    Optional<TestSession> findByUserIdAndTestId(String userId, String testId);
}
