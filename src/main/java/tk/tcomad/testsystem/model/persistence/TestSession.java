package tk.tcomad.testsystem.model.persistence;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TestSession implements Serializable {

    private final static long serialVersionUID = 13254083253534L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testId;

    private String userId;

    private Instant startTime;

    private Integer elapsedTime;

    private Float score;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<Question> questions;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<Long, Set<String>> userAnswersByQuestionId;
}
