package in.HMS.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import in.HMS.Entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByStatus(String status);
}
