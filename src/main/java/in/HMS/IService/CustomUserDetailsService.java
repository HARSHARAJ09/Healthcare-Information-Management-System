package in.HMS.IService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.HMS.Entity.Admin;
import in.HMS.Entity.Doctor;
import in.HMS.Entity.Patient;
import in.HMS.Repository.AdminRepository;
import in.HMS.Repository.DoctorRepository;
import in.HMS.Repository.PatientRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepo patientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByEmail(email).orElse(null);
        if (admin != null) {
            return new CustomUserDetails(admin);
        }

        Doctor doctor = doctorRepository.findByEmail(email).orElse(null);
        if (doctor != null) {
            return new CustomUserDetails(doctor);
        }

        Patient patient = patientRepository.findByEmail(email).orElse(null);
        if (patient != null) {
            return new CustomUserDetails(patient);
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
