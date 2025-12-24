package in.HMS.Response;

import lombok.Data;

@Data
public class DoctorActiveAppointmentResponse {

    private Integer appointmentId;

    private String patientName;
    private Integer patientAge;
    private String patientGender;

    private String symptoms;
}
