package tk.tcomad.testsystem.model.domain;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Test {

    private String id;
    private String name;
    private String authorId;
    private Integer durationMinutes;
    private Integer questionsNumber;
    private List<Question> questions;
    private List<TestSession> testSessions;
}
