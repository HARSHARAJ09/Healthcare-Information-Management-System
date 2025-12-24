package in.HMS.Response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PatientAppointmentResponse {

    private Integer appointmentId;

    private String doctorName;     // set when allocated
    private String symptoms;
    private String prescription;   // visible only if completed
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
}
