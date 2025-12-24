package in.HMS.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientId;

    private String patientName;

    @Column(unique = true, nullable = false)
    private String patientEmail;

    private String patientPhone;
    private String patientGender;
    private Integer patientAge;
    private String patientAddress;

    //  Link to login credentials
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
