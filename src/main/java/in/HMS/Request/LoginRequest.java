package in.HMS.Request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

