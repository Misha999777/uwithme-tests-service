package tk.tcomad.testsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Test implements Serializable {

    private final static long serialVersionUID = 73091263434L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String name;

    private String authorId;

    private Integer durationMinutes;

    private Integer questionsNumber;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "test",
            orphanRemoval = true)
    private Set<Question> questions;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "test",
            orphanRemoval = true)
    private Set<TestSession> testSessions;
}
