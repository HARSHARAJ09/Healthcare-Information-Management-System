package in.HMS.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorSignupRequest {

    @NotBlank
    private String doctorName;

    @NotBlank
    private String specialization;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
