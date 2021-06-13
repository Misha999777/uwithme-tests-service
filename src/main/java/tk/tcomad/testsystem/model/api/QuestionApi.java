package tk.tcomad.testsystem.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class QuestionApi {

    Long id;
    String text;
    List<AnswerApi> answers;
    String testId;
    Boolean isMultipleChoice;
}
