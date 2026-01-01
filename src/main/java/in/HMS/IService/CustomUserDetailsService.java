package in.HMS.IService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.hms.entity.Admin;
import in.hms.entity.Doctor;
import in.hms.entity.Patient;
import in.hms.repository.AdminRepository;
import in.hms.repository.DoctorRepository;
import in.hms.repository.PatientRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

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
