package in.HMS.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.HMS.Entity.Appointment;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStatus(String status);

    List<Appointment> findByPatientPatientId(Long patientId);

    List<Appointment> findByDoctorDoctorId(Long doctorId);
}

