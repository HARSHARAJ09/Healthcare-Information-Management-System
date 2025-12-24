package in.HMS.IService.Imp;

import org.springframework.stereotype.Service;

import in.HMS.Entity.Patient;
import in.HMS.Repository.PatientRepo;
import in.HMS.IService.IPatient;

@Service
public class PatientService implements IPatient {

    private final PatientRepo patientRepository;

    public PatientService(PatientRepo patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient registerPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient findByUserId(Integer userId) {
        return patientRepository.findByUser_UserId(userId);
    }
}
