package in.HMS.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorPrescriptionRequest {

    @NotBlank
    private String prescription;
}
