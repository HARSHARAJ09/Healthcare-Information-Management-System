package in.HMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.HMS.Entity.Patient;

public interface PatientRepo extends JpaRepository<Patient, Integer>{

}
