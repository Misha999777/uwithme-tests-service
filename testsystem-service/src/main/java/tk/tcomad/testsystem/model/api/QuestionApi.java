package tk.tcomad.testsystem.model.api;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class QuestionApi {

    private Long id;
    private String text;
    private List<AnswerApi> answers;
    private String testId;
    private Boolean isMultipleChoice;
}
