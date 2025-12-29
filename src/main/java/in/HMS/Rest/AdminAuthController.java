package in.HMS.Rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import in.HMS.Entity.User;
import in.HMS.Exception.UserException;
import in.HMS.IService.CustomUserDetails;
import in.HMS.Repository.UserRepository;
import in.HMS.Request.AdminLoginRequest;
import in.HMS.Response.AuthResponse;
import in.HMS.Utils.JwtUtil;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AdminAuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserRepository userRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AdminLoginRequest request) {

        // 1️⃣ authenticate credentials
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        // 2️⃣ store authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3️⃣ get authenticated user
        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() ->
                        new UserException("Admin not found", HttpStatus.NOT_FOUND));

        if (!"ROLE_ADMIN".equals(user.getRole())) {
            throw new UserException("Not an Admin account", HttpStatus.FORBIDDEN);
        }

        // 4️⃣ create token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("role", user.getRole());

        String token = jwtUtil.generateToken(user.getEmail(), claims);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
