package in.HMS.Rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import in.HMS.Entity.Patient;
import in.HMS.Entity.User;
import in.HMS.Exception.UserException;
import in.HMS.Repository.UserRepository;
import in.HMS.Request.PatientLoginRequest;
import in.HMS.Request.PatientSignupRequest;
import in.HMS.Response.AuthResponse;
import in.HMS.IService.IPatient;
import in.HMS.Utils.JwtUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patient")
public class PatientAuthController {

    private final UserRepository userRepository;
    private final IPatient patientService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public PatientAuthController(
            UserRepository userRepository,
            IPatient patientService,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.patientService = patientService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 🔓 PATIENT SIGNUP
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @Valid @RequestBody PatientSignupRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            throw new UserException("Invalid patient input", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserException("Email already exists", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_PATIENT");
        user = userRepository.save(user);

        Patient patient = new Patient();
        patient.setPatientName(request.getPatientName());
        patient.setPatientEmail(request.getEmail());
        patient.setPatientPhone(request.getPhone());
        patient.setPatientGender(request.getGender());
        patient.setPatientAge(request.getAge());
        patient.setPatientAddress(request.getAddress());
        patient.setUser(user);

        patientService.registerPatient(patient);

        return ResponseEntity.ok("Patient registered successfully");
    }

    //  PATIENT LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody PatientLoginRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            throw new UserException("Invalid login input", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserException("Email not found", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        if (!"ROLE_PATIENT".equals(user.getRole())) {
            throw new UserException("Not a patient account", HttpStatus.FORBIDDEN);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("role", user.getRole());

        String token = jwtUtil.generateToken(user.getEmail(), claims);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
