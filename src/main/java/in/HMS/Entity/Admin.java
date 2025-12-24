package in.HMS.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    private String name;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
