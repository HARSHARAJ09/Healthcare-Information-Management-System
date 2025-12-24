package in.HMS.IService.Imp;

import org.springframework.stereotype.Service;

import in.HMS.Entity.Doctor;
import in.HMS.IService.IDoctorService;
import in.HMS.Repository.DoctorRepository;

@Service
public class DoctorServiceImp implements IDoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImp(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor registerDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor findByUserId(Integer userId) {
        return doctorRepository.findByUser_UserId(userId);
    }
}
