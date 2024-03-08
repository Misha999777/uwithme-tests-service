package tk.tcomad.testsystem.model.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tk.tcomad.testsystem.model.domain.Answer;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class QuestionDb implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testId;

    private String text;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Answer> answers;
}
