package in.HMS.Response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppointmentResponse {
    private Long appointmentId;
    private String doctorName;
    private String status;
    private String prescription;
    private String symptoms;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
}

