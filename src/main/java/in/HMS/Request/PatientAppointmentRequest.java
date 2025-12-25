package in.HMS.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientAppointmentRequest {

    @NotBlank
    private String symptoms;
}
