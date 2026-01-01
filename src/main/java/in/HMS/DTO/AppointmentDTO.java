package in.HMS.DTO;


import java.time.LocalDateTime;

import in.HMS.Entity.Doctor;
import in.HMS.Entity.Patient;
import lombok.Data;



@Data
public class AppointmentDTO {
    private Long appointmentId;
    private Patient patient;
    private Doctor doctor;
    private String symptoms;
    private String prescription;
    private String status; // NEW, PENDING, ACTIVE, COMPLETED
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
}
