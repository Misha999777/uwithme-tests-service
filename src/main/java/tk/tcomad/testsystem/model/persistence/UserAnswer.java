package tk.tcomad.testsystem.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserAnswer implements Serializable {

    private final static long serialVersionUID = 132540835467612L;

    private Long questionId;

    private List<String> answerTexts;
}
