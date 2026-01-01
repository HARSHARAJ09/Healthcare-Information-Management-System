package in.HMS.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(nullable = false, length = 5000)
    private String symptoms;

    @Column(length = 5000)
    private String prescription;

    @Column(nullable = false)
    private String status; 
    // NEW, PENDING, ACTIVE, COMPLETED

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;
}
