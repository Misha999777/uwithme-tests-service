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
public class UserAnswerApi {

    private Long questionId;
    private List<String> answerTexts;
}
