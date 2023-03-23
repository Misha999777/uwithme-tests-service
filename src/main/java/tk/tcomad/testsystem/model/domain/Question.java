package tk.tcomad.testsystem.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Question {

    private Long id;
    private String testId;
    private String text;
    private boolean multipleChoice;
    private List<Answer> answers;
}
