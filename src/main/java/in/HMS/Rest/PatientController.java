package in.HMS.Rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



import in.HMS.Entity.Appointment;
import in.HMS.Exception.PatientException;
import in.HMS.IService.CustomUserDetails;
import in.HMS.IService.IAppointmentService;
import in.HMS.IService.IPatient;
import in.HMS.Request.AppointmentRequest;
import in.HMS.Response.ApiResponse;
import in.HMS.Response.AppointmentResponse;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IPatient patientService;

    @PostMapping("/appointments")
    public ApiResponse createAppointment(
            @RequestBody AppointmentRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Appointment appointment = new Appointment();
        appointment.setPatient(
                patientService.findById(userDetails.getEntityId())
        );
        appointment.setSymptoms(request.getSymptoms());
        appointment.setStatus("NEW");
        appointment.setCreatedAt(LocalDateTime.now());

        appointmentService.create(appointment);

        return new ApiResponse("Appointment created successfully");
    }

    @GetMapping("/appointments")
    public List<AppointmentResponse> getAppointments(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return appointmentService.findByPatientId(userDetails.getEntityId())
                .stream().map(this::map).toList();
    }

    @GetMapping("/appointments/latest-prescription")
    public String getLatestPrescription(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return appointmentService.findByPatientId(userDetails.getEntityId())
                .stream()
                .map(Appointment::getPrescription)
                .filter(Objects::nonNull)
                .reduce((a, b) -> b)
                .orElseThrow(() ->
                        new PatientException("No prescription found", null)
                );
    }

    private AppointmentResponse map(Appointment a) {
        AppointmentResponse r = new AppointmentResponse();
        r.setAppointmentId(a.getAppointmentId());
        r.setDoctorName(
                a.getDoctor() != null ? a.getDoctor().getDoctorName() : null);
        r.setStatus(a.getStatus());
        r.setPrescription(a.getPrescription());
        r.setSymptoms(a.getSymptoms());
        r.setCreatedAt(a.getCreatedAt());
        r.setCompletedAt(a.getCompletedAt());
        return r;
    }
}
