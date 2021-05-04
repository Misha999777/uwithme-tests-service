package tk.tcomad.testsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.tcomad.testsystem.model.persistence.Test;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends CrudRepository<Test, String> {

    List<Test> findAllByAuthorId(String authorId);

    void deleteByAuthorIdAndId(String authorId, String id);

    Optional<Test> findByAuthorIdAndId(String authorId, String id);
}
