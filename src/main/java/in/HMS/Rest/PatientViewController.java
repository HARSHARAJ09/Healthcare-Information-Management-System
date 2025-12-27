package in.HMS.Rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import in.HMS.Entity.Appointment;
import in.HMS.Entity.Patient;
import in.HMS.Entity.User;
import in.HMS.Exception.PatientException;
import in.HMS.IService.IPatient;
import in.HMS.IService.IPatientAppointmentService;

import in.HMS.Response.ApiResponse;
import in.HMS.Response.PatientAppointmentResponse;

@RestController
@RequestMapping("/api/patient")
public class PatientViewController {

    private final IPatient patientService;
    private final IPatientAppointmentService appointmentService;

    public PatientViewController(
            IPatient patientService,
            IPatientAppointmentService appointmentService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    //  VIEW ALL APPOINTMENTS
    @GetMapping("/appointments")
    public ApiResponse<List<PatientAppointmentResponse>> getAppointments(@AuthenticationPrincipal
            Authentication authentication) {

        Integer userId = ((User) authentication.getPrincipal()).getUserId();

        Patient patient = patientService.findByUserId(userId);
        if (patient == null) {
            throw new PatientException("Patient not found", HttpStatus.NOT_FOUND);
        }

        List<PatientAppointmentResponse> response =
                appointmentService.getPatientAppointments(patient.getPatientId())
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return new ApiResponse<>("success", "Patient appointments", response);
    }

    // 🔐 VIEW LATEST PRESCRIPTION
    @GetMapping("/prescription/latest")
    public ApiResponse<PatientAppointmentResponse> getLatestPrescription(@AuthenticationPrincipal
            Authentication authentication) {

        Integer userId = ((User) authentication.getPrincipal()).getUserId();

        Patient patient = patientService.findByUserId(userId);
        if (patient == null) {
            throw new PatientException("Patient not found", HttpStatus.NOT_FOUND);
        }

        Appointment appointment =
                appointmentService.getLatestCompletedAppointment(patient.getPatientId());

        if (appointment == null) {
            throw new PatientException("No completed treatment found", HttpStatus.NOT_FOUND);
        }

        return new ApiResponse<>(
                "success",
                "Latest prescription",
                mapToResponse(appointment));
    }

    // 🔁 Mapper
    private PatientAppointmentResponse mapToResponse(@AuthenticationPrincipal Appointment appointment) {

        PatientAppointmentResponse res = new PatientAppointmentResponse();
        res.setAppointmentId(appointment.getAppointmentId());
        res.setSymptoms(appointment.getSymptoms());
        res.setPrescription(appointment.getPrescription());
        res.setStatus(appointment.getStatus());
        res.setCreatedAt(appointment.getCreatedAt());
        res.setCompletedAt(appointment.getCompletedAt());

        if (appointment.getDoctor() != null) {
            res.setDoctorName(appointment.getDoctor().getDoctorName());
        }

        return res;
    }
}
