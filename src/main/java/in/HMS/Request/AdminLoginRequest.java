package in.HMS.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminLoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
