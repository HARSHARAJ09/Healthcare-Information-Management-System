package in.HMS.Rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import in.HMS.Entity.Admin;
import in.HMS.Entity.User;
import in.HMS.Exception.UserException;
import in.HMS.Repository.UserRepository;
import in.HMS.Request.AdminLoginRequest;
import in.HMS.Request.AdminSignupRequest;
import in.HMS.Response.AuthResponse;
import in.HMS.IService.IAdminService;
import in.HMS.Utils.JwtUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    //ADMIN SIGNUP
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @Valid @RequestBody AdminSignupRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new UserException("Invalid Admin Input", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserException("Email already exists", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_ADMIN");
        user = userRepository.save(user);

        Admin admin = new Admin();
        admin.setName(request.getName());
        admin.setUser(user);
        adminService.registerAdmin(admin);

        return ResponseEntity.ok("Admin registered successfully");
    }

    // ADMIN LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AdminLoginRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new UserException("Invalid Login Input", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException("Invalid Email", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException("Invalid Password", HttpStatus.UNAUTHORIZED);
        }

        if (!user.getRole().equals("ROLE_ADMIN")) {
            throw new UserException("Not an Admin account", HttpStatus.FORBIDDEN);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("role", user.getRole());

        String token = jwtUtil.generateToken(user.getEmail(), claims);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
