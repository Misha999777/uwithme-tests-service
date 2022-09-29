package tk.tcomad.testsystem.model.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import tk.tcomad.testsystem.model.domain.Answer;

@Entity
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class QuestionDb implements Serializable {

    private final static long serialVersionUID = 73251433534L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Answer> answers;
    private String testId;
}
