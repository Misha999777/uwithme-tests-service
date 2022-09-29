package tk.tcomad.testsystem.model.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Question {

    private Long id;
    private String text;
    private List<Answer> answers;
    private String testId;
}
