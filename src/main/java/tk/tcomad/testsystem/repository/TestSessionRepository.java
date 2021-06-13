package tk.tcomad.testsystem.repository;

import org.springframework.data.repository.CrudRepository;
import tk.tcomad.testsystem.model.persistence.TestSession;

public interface TestSessionRepository extends CrudRepository<TestSession, Long> {
}
