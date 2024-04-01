package tk.tcomad.testsystem.model.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class TestDb implements Serializable {

    @Id
    @UuidGenerator
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
