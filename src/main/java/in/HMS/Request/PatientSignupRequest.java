package in.HMS.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientSignupRequest {

    @NotBlank
    private String patientName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String phone;
    private String gender;
    private Integer age;
    private String address;
}
