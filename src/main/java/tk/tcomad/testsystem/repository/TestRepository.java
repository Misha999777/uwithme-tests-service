package tk.tcomad.testsystem.repository;

import org.springframework.data.repository.CrudRepository;
import tk.tcomad.testsystem.model.persistence.TestDb;

import java.util.List;
import java.util.Optional;

public interface TestRepository extends CrudRepository<TestDb, String> {

    List<TestDb> findAllByAuthorIdOrderByCreateDateDesc(String authorId);

    void deleteByAuthorIdAndId(String authorId, String id);

    Optional<TestDb> findByAuthorIdAndId(String authorId, String id);
}
