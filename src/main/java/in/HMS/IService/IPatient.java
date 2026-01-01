package in.HMS.IService;

import java.util.List;

import in.HMS.Entity.Patient;


public interface IPatient{

    Patient create(Patient patient);

    Patient findByEmail(String email);

    Patient findById(Long patientId);

    List<Patient> findAll();
}
