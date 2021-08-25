package tk.tcomad.testsystem.model.persistence;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Answer implements Serializable {

    private final static long serialVersionUID = 732569237343534L;

    private String text;
    private boolean correct;
}
