package in.HMS.Rest;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import in.HMS.Entity.Appointment;
import in.HMS.Entity.Doctor;
import in.HMS.Entity.Patient;
import in.HMS.Entity.User;
import in.HMS.Exception.DoctorException;
import in.HMS.IService.IDoctorAppointmentService;
import in.HMS.IService.IDoctorService;
import in.HMS.Response.ApiResponse;
import in.HMS.Response.DoctorActiveAppointmentResponse;

@RestController
@RequestMapping("/api/doctor")
public class DoctorAppointmentController {

    private final IDoctorService doctorService;
    private final IDoctorAppointmentService appointmentService;

    public DoctorAppointmentController(
            IDoctorService doctorService,
            IDoctorAppointmentService appointmentService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    // 🔐 VIEW ACTIVE APPOINTMENT
    @GetMapping("/appointment/active")
    public ApiResponse<?> getActiveAppointment(Authentication authentication) {

        Integer userId = ((User) authentication.getPrincipal()).getUserId();

        Doctor doctor = doctorService.findByUserId(userId);
        if (doctor == null) {
            throw new DoctorException("Doctor not found", HttpStatus.NOT_FOUND);
        }

        Appointment appointment =
                appointmentService.getActiveAppointment(doctor.getDoctorId());

        if (appointment == null) {
            return new ApiResponse<>(
                    "success",
                    "No active appointment",
                    null
            );
        }

        Patient patient = appointment.getPatient();

        DoctorActiveAppointmentResponse response =
                new DoctorActiveAppointmentResponse();
        response.setAppointmentId(appointment.getAppointmentId());
        response.setPatientName(patient.getPatientName());
        response.setPatientAge(patient.getPatientAge());
        response.setPatientGender(patient.getPatientGender());
        response.setSymptoms(appointment.getSymptoms());

        return new ApiResponse<>(
                "success",
                "Active appointment details",
                response
        );
    }
}
