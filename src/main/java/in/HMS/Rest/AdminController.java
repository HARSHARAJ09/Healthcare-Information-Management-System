package in.hms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import in.hms.dto.ApiResponse;
import in.hms.dto.AppointmentResponse;
import in.hms.entity.Appointment;
import in.hms.exception.AdminException;
import in.hms.security.CustomUserDetails;
import in.hms.service.IAppointmentService;
import in.hms.service.IDoctorService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IDoctorService doctorService;

    @GetMapping("/appointments/new")
    public List<AppointmentResponse> getNewAppointments(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return appointmentService.findByStatus("NEW")
                .stream()
                .map(this::map)
                .toList();
    }

    @PutMapping("/appointments/{appointmentId}/assign/{doctorId}")
    public ApiResponse assignDoctor(
            @PathVariable Long appointmentId,
            @PathVariable Long doctorId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Appointment appointment = appointmentService.findById(appointmentId);
        if (appointment == null) {
            throw new AdminException("Appointment not found", null);
        }

        appointment.setDoctor(doctorService.findById(doctorId));
        appointment.setStatus("ACTIVE");
        appointmentService.update(appointment);

        return new ApiResponse("Doctor assigned successfully");
    }

    @GetMapping("/appointments/pending")
    public List<AppointmentResponse> getPendingAppointments() {
        return appointmentService.findByStatus("PENDING")
                .stream().map(this::map).toList();
    }

    @GetMapping("/appointments/completed")
    public List<AppointmentResponse> getCompletedAppointments() {
        return appointmentService.findByStatus("COMPLETED")
                .stream().map(this::map).toList();
    }

    private AppointmentResponse map(Appointment a) {
        AppointmentResponse r = new AppointmentResponse();
        r.setAppointmentId(a.getAppointmentId());
        r.setStatus(a.getStatus());
        r.setPrescription(a.getPrescription());
        r.setSymptoms(a.getSymptoms());
        r.setCreatedAt(a.getCreatedAt());
        r.setCompletedAt(a.getCompletedAt());
        r.setDoctorName(
                a.getDoctor() != null ? a.getDoctor().getDoctorName() : null
        );
        return r;
    }
}
