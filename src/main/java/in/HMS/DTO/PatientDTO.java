package in.HMS.DTO;

import lombok.Data;


@Data
public class PatientDTO {

    private Long patientId;
    private String patientName;
    private String email;
    private String password;
    private Integer age;
    private String gender;
    private String phone;
    private String address;
    private String role = "ROLE_PATIENT";
}
