package in.HMS.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.HMS.Entity.Patient;



@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);
}

