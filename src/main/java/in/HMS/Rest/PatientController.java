package in.HMS.Rest;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import in.HMS.Entity.Appointment;
import in.HMS.Entity.Patient;
import in.HMS.Exception.PatientException;
import in.HMS.IService.IAppointmentService;
import in.HMS.IService.IPatient;

import in.HMS.Request.PatientAppointmentRequest;
import in.HMS.Response.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final IPatient patientService;
    private final IAppointmentService appointmentService;

    public PatientController(
            IPatient patientService,
            IAppointmentService appointmentService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    // 🔐 SUBMIT SYMPTOMS / CREATE APPOINTMENT
    @PostMapping("/appointment")
    public ApiResponse<?> createAppointment(
            @Valid @RequestBody PatientAppointmentRequest request,
            Authentication authentication) {

        // Extract logged-in userId from JWT
        Integer userId = ((in.HMS.Entity.User) authentication.getPrincipal()).getUserId();

        Patient patient = patientService.findByUserId(userId);
        if (patient == null) {
            throw new PatientException("Patient not found", HttpStatus.NOT_FOUND);
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setSymptoms(request.getSymptoms());
        appointment.setStatus("NEW");
        appointment.setCreatedAt(LocalDateTime.now());

        appointmentService.createAppointment(appointment);

        return new ApiResponse<>(
                "success",
                "Appointment request submitted successfully",
                null
        );
    }
}
