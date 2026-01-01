package in.HMS.Rest;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import in.HMS.Entity.Appointment;
import in.HMS.Exception.DoctorException;
import in.HMS.IService.CustomUserDetails;
import in.HMS.IService.IAppointmentService;
import in.HMS.Response.ApiResponse;
import in.HMS.Response.AppointmentResponse;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private IAppointmentService appointmentService;

    @GetMapping("/appointments/active")
    public AppointmentResponse getActiveAppointment(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return appointmentService.findByDoctorId(userDetails.getEntityId())
                .stream()
                .filter(a -> "ACTIVE".equals(a.getStatus()))
                .findFirst()
                .map(this::map)
                .orElse(null);
    }

    @PutMapping("/appointments/{id}/prescription")
    public ApiResponse addPrescription(
            @PathVariable Long id,
            @RequestBody String prescription,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Appointment appointment = appointmentService.findById(id);

        if (!appointment.getDoctor().getDoctorId()
                .equals(userDetails.getEntityId())) {
            throw new DoctorException("Unauthorized access", null);
        }

        appointment.setPrescription(prescription);
        appointment.setStatus("ACTIVE");
        appointmentService.update(appointment);

        return new ApiResponse("Prescription added");
    }

    @PutMapping("/appointments/{id}/complete")
    public ApiResponse completeAppointment(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Appointment appointment = appointmentService.findById(id);

        appointment.setStatus("COMPLETED");
        appointment.setCompletedAt(LocalDateTime.now());
        appointmentService.update(appointment);

        return new ApiResponse("Appointment completed");
    }

    private AppointmentResponse map(Appointment a) {
        AppointmentResponse r = new AppointmentResponse();
        r.setAppointmentId(a.getAppointmentId());
        r.setDoctorName(a.getDoctor().getDoctorName());
        r.setSymptoms(a.getSymptoms());
        r.setStatus(a.getStatus());
        r.setPrescription(a.getPrescription());
        r.setCreatedAt(a.getCreatedAt());
        r.setCompletedAt(a.getCompletedAt());
        return r;
    }
}
