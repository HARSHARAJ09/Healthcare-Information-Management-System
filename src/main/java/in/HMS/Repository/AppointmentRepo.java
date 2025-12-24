package in.HMS.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.HMS.Entity.Appointment;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByPatient_PatientId(Integer patientId);

    Appointment findTopByPatient_PatientIdAndStatusOrderByCompletedAtDesc(
            Integer patientId, String status);
}
