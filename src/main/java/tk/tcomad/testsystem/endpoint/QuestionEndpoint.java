package tk.tcomad.testsystem.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("question")
@AllArgsConstructor
public class QuestionEndpoint {


}
