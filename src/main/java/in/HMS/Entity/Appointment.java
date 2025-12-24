package in.HMS.Entity;

<<<<<<< HEAD
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor; // allocated later by admin

    @Column(nullable = false, length = 5000)
    private String symptoms;

    @Column(length = 5000)
    private String prescription; // filled by doctor later

    private String status; 
    // NEW, PENDING, ACTIVE, COMPLETED

    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
=======
public class Appointment {

>>>>>>> 153b6c1f027ee28d6ec36ec93006c6afdb952be0
}
