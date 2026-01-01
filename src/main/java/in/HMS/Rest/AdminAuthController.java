package in.HMS.Rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import in.hms.dto.AdminSignupRequest;
import in.hms.dto.JwtResponse;
import in.hms.dto.LoginRequest;
import in.hms.entity.Admin;
import in.hms.exception.AuthException;
import in.hms.security.JwtUtil;
import in.hms.service.IAdminService;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(
            @RequestBody AdminSignupRequest request) {

        if (adminService.findByEmail(request.getEmail()) != null) {
            throw new AuthException("Email already exists", HttpStatus.CONFLICT);
        }

        Admin admin = new Admin();
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        Admin saved = adminService.create(admin);

        String token = jwtUtil.generateToken(
                saved.getEmail(),
                Map.of(
                        "role", saved.getRole(),
                        "entityId", saved.getAdminId()
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

        Admin admin = adminService.findByEmail(request.getEmail());

        String token = jwtUtil.generateToken(
                admin.getEmail(),
                Map.of(
                        "role", admin.getRole(),
                        "entityId", admin.getAdminId()
                )
        );

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
