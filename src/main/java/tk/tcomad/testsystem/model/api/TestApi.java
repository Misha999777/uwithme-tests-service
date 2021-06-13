package tk.tcomad.testsystem.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TestApi {

    String id;
    String name;
    String authorId;
    Integer durationMinutes;
    Integer questionsNumber;
    Set<QuestionApi> questions;
    Set<TestSessionApi> testSessions;
}
