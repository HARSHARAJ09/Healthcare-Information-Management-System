package in.HMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;

    private String doctorName;

    @Column(nullable = false)
    private String specialization;

    // Doctor availability
    private Boolean isBusy = false;

    //  Login mapping
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
