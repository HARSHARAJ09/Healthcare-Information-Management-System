package in.HMS.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorLoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
