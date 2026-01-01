package in.HMS.IService;

import in.HMS.Entity.Doctor;

public interface IDoctorService {

    Doctor create(Doctor doctor);

    Doctor findByEmail(String email);

    Doctor findById(Long doctorId);

    Doctor update(Doctor doctor);
}
