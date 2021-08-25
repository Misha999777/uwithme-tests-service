package tk.tcomad.testsystem.model.api;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TestSessionApi {

    Long id;
    Integer durationMinutes;
    String userId;
    String userName;
    Instant startTime;
    Integer elapsedTime;
    Float score;
    Set<QuestionApi> questions;
    Map<Long, Set<String>> userAnswersByQuestionId;
}
