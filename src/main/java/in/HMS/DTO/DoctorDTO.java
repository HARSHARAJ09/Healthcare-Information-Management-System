package in.HMS.DTO;


import lombok.Data;


@Data
public class DoctorDTO {

    private Long doctorId;
    private String doctorName;
    private String specialization;
    private String email;
    private String password;
    private boolean isBusy = false;
    private String role = "ROLE_DOCTOR";
}
