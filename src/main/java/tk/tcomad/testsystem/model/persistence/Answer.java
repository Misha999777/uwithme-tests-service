package tk.tcomad.testsystem.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Answer implements Serializable {

    private final static long serialVersionUID = 732569237343534L;

    private String text;

    private boolean correct;
}
