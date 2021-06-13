package tk.tcomad.testsystem.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TestSessionApi {

    Long id;
    Integer durationMinutes;
    String userId;
    Instant startTime;
    Integer elapsedTime;
    Float score;
    Set<QuestionApi> questions;
    Set<UserAnswerApi> userAnswers;
}
