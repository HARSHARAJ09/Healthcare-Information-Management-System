package in.HMS.IService;

import in.HMS.Entity.Doctor;

public interface IDoctorService {

    Doctor registerDoctor(Doctor doctor);

    Doctor findByUserId(Integer userId);
}
