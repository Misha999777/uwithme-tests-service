package tk.tcomad.testsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakUserApi {

    private String id;
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
}
