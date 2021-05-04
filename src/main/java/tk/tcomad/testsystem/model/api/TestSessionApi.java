package tk.tcomad.testsystem.model.api;

import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
public class TestSessionApi {

    Long id;
    Integer durationMinutes;
    String userId;
    Date startTime;
    Float score;
    List<QuestionApi> questions;
    List<UserAnswerApi> userAnswers;
}
