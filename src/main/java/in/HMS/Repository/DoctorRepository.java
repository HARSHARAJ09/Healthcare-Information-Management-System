package in.HMS.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.HMS.Entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Doctor findByUser_UserId(Integer userId);

    List<Doctor> findBySpecializationAndIsBusyFalse(String specialization);
}
