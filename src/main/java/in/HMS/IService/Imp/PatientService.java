package in.HMS.IService.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.HMS.Entity.Patient;
import in.HMS.IService.IPatient;
import in.HMS.Repository.PatientRepo;

@Service
public class PatientService implements IPatient{

    @Autowired
    private PatientRepo patientRepository;

    @Override
    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Patient findById(Long patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }
}
