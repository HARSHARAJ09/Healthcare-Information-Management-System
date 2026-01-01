package in.hms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import in.hms.dto.JwtResponse;
import in.hms.dto.LoginRequest;
import in.hms.dto.PatientSignupRequest;
import in.hms.entity.Patient;
import in.hms.exception.AuthException;
import in.hms.security.JwtUtil;
import in.hms.service.IPatientService;

@RestController
@RequestMapping("/api/patient/auth")
public class PatientAuthController {

    @Autowired
    private IPatientService patientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(
            @RequestBody PatientSignupRequest request) {

        if (patientService.findByEmail(request.getEmail()) != null) {
            throw new AuthException("Email already exists", HttpStatus.CONFLICT);
        }

        Patient patient = new Patient();
        patient.setPatientName(request.getPatientName());
        patient.setEmail(request.getEmail());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setPhone(request.getPhone());
        patient.setAddress(request.getAddress());

        Patient saved = patientService.create(patient);

        String token = jwtUtil.generateToken(
                saved.getEmail(),
                Map.of(
                        "role", saved.getRole(),
                        "entityId", saved.getPatientId()
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

        Patient patient = patientService.findByEmail(request.getEmail());

        String token = jwtUtil.generateToken(
                patient.getEmail(),
                Map.of(
                        "role", patient.getRole(),
                        "entityId", patient.getPatientId()
                )
        );

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
