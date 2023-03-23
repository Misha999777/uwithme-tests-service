package tk.tcomad.testsystem.model.persistence;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class TestDb implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String name;

    private String authorId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private Integer durationMinutes;

    private Integer questionsNumber;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "testId")
    private List<QuestionDb> questions;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "testId")
    private List<TestSessionDb> results;
}
