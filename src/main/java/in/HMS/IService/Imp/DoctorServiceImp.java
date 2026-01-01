package in.HMS.IService.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.HMS.Entity.Doctor;
import in.HMS.IService.IDoctorService;
import in.HMS.Repository.DoctorRepository;

@Service
public class DoctorServiceImp implements IDoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Doctor create(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Doctor findById(Long doctorId) {
        return doctorRepository.findById(doctorId).orElse(null);
    }

    @Override
    public Doctor update(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
}
