package in.HMS.Rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import in.HMS.Entity.Patient;
import in.HMS.Entity.User;
import in.HMS.Exception.UserException;
import in.HMS.IService.IPatient;
import in.HMS.IService.CustomUserDetails;
import in.HMS.IService.CustomUserDetailsService;
import in.HMS.Repository.UserRepository;
import in.HMS.Request.PatientLoginRequest;
import in.HMS.Request.PatientSignupRequest;
import in.HMS.Response.AuthResponse;
import in.HMS.Utils.JwtUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patient")
public class PatientAuthController {

    private final UserRepository userRepository;
    private final IPatient patientService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    public PatientAuthController(
            UserRepository userRepository,
            IPatient patientService,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService) {

        this.userRepository = userRepository;
        this.patientService = patientService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }

    /* -------------------- SIGNUP (unchanged) -------------------- */
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

    /* -------------------- LOGIN (rewritten) -------------------- */
 

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody PatientLoginRequest req) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        String token = jwtUtil.generateToken(
                userDetails.getUsername(),
                Map.of(
                    "userId", userDetails.getUser().getUserId(),
                    "role", userDetails.getUser().getRole()
                )
        );

        return ResponseEntity.ok(new AuthResponse(token));
    }
}