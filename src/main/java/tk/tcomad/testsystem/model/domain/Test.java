package tk.tcomad.testsystem.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Test {

    private String id;
    private String name;
    private String authorId;
    private Date createDate;
    private Integer durationMinutes;
    private Integer questionsNumber;
}
