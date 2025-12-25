package in.HMS.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.HMS.Entity.Patient;

public interface PatientRepo extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByPatientEmail(String patientEmail);

    Patient findByUser_UserId(Integer userId);
}
