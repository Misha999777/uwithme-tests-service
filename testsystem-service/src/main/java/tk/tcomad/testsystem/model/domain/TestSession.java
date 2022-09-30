package tk.tcomad.testsystem.model.domain;

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
public class TestSession {

    private Long id;
    private String testId;
    private Integer durationMinutes;
    private String userId;
    private String userName;
    private Instant startTime;
    private Integer elapsedTime;
    private Float score;
    private Set<Question> questionSnapshots;
    private Map<Long, Set<String>> userAnswersByQuestionId;
}
