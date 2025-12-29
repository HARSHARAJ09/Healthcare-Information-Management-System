package in.HMS.Rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import in.HMS.Entity.Doctor;
import in.HMS.Entity.User;
import in.HMS.Exception.UserException;
import in.HMS.IService.IDoctorService;
import in.HMS.Repository.UserRepository;
import in.HMS.Request.DoctorLoginRequest;
import in.HMS.Request.DoctorSignupRequest;
import in.HMS.Response.AuthResponse;
import in.HMS.Utils.JwtUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctor")
public class DoctorAuthController {

    private final UserRepository userRepository;
    private final IDoctorService doctorService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public DoctorAuthController(
            UserRepository userRepository,
            IDoctorService doctorService,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.doctorService = doctorService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    /* ---------------- SIGNUP (unchanged) ---------------- */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @Valid @RequestBody DoctorSignupRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            throw new UserException("Invalid doctor input", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserException("Email already exists", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_DOCTOR");
        user = userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setDoctorName(request.getDoctorName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setIsBusy(false);
        doctor.setUser(user);

        doctorService.registerDoctor(doctor);

        return ResponseEntity.ok("Doctor registered successfully");
    }

    /* ---------------- LOGIN (rewritten) ---------------- */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody DoctorLoginRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            throw new UserException("Invalid login input", HttpStatus.BAD_REQUEST);
        }

        // 1️⃣ Authenticate credentials using Spring Security
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        // 2️⃣ Store user in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3️⃣ Load user from DB (role validation)
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserException("Email not found", HttpStatus.NOT_FOUND));

        if (!"ROLE_DOCTOR".equals(user.getRole())) {
            throw new UserException("Not a doctor account", HttpStatus.FORBIDDEN);
        }

        // 4️⃣ Generate JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("role", user.getRole());

        String token = jwtUtil.generateToken(user.getEmail(), claims);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
