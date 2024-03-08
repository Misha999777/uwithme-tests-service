package tk.tcomad.testsystem.model.persistence;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tk.tcomad.testsystem.model.domain.Question;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

@Entity
@Data
public class TestSessionDb implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testId;

    private String userId;

    private Instant startTime;

    private Integer elapsedTime;

    private Float score;

    @JdbcTypeCode(SqlTypes.JSON)
    private Set<Question> questionSnapshots;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<Long, Set<String>> userAnswersByQuestionId;
}
