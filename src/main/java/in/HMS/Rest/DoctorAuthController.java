package in.hms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import in.hms.dto.DoctorSignupRequest;
import in.hms.dto.JwtResponse;
import in.hms.dto.LoginRequest;
import in.hms.entity.Doctor;
import in.hms.exception.AuthException;
import in.hms.security.JwtUtil;
import in.hms.service.IDoctorService;

@RestController
@RequestMapping("/api/doctor/auth")
public class DoctorAuthController {

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(
            @RequestBody DoctorSignupRequest request) {

        if (doctorService.findByEmail(request.getEmail()) != null) {
            throw new AuthException("Email already exists", HttpStatus.CONFLICT);
        }

        Doctor doctor = new Doctor();
        doctor.setDoctorName(request.getDoctorName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setEmail(request.getEmail());
        doctor.setPassword(passwordEncoder.encode(request.getPassword()));

        Doctor saved = doctorService.create(doctor);

        String token = jwtUtil.generateToken(
                saved.getEmail(),
                Map.of(
                        "role", saved.getRole(),
                        "entityId", saved.getDoctorId()
                )
        );

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Doctor doctor = doctorService.findByEmail(request.getEmail());

        String token = jwtUtil.generateToken(
                doctor.getEmail(),
                Map.of(
                        "role", doctor.getRole(),
                        "entityId", doctor.getDoctorId()
                )
        );

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
