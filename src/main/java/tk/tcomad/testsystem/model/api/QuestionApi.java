package tk.tcomad.testsystem.model.api;

import lombok.Value;

import java.util.List;

@Value
public class QuestionApi {

    Long id;
    String text;
    List<AnswerApi> answers;
    String testId;
}
