package tk.tcomad.testsystem.model.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TestDb implements Serializable {

    private final static long serialVersionUID = 73091263434L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private String name;
    private String authorId;
    private Integer durationMinutes;
    private Integer questionsNumber;
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "testId")
    private List<QuestionDb> questions;
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "testId")
    private List<TestSessionDb> testSessions;
}
