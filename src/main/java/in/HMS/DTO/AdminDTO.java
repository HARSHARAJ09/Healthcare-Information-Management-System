package in.HMS.DTO;

import lombok.Data;

@Data
public class AdminDTO {

    private Long adminId;
    private String name;
    private String email;
    private String password;
    private String role = "ROLE_ADMIN";
}
