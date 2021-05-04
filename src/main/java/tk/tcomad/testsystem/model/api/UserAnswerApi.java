package tk.tcomad.testsystem.model.api;

import lombok.Value;

@Value
public class UserAnswerApi {

    Long questionId;
    String answerText;
}
