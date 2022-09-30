package tk.tcomad.testsystem.model.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class TestDb implements Serializable {

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
