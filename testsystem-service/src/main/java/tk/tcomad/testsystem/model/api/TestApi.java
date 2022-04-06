package tk.tcomad.testsystem.model.api;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TestApi {

    private String id;
    private String name;
    private String authorId;
    private Integer durationMinutes;
    private Integer questionsNumber;
    private Set<QuestionApi> questions;
    private Set<TestSessionApi> testSessions;
}
