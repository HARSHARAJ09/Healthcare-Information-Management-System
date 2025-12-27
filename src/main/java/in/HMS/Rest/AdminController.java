package in.HMS.Rest;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import in.HMS.Entity.Appointment;
import in.HMS.Entity.Doctor;
import in.HMS.Exception.AdminException;
import in.HMS.Repository.AppointmentRepository;
import in.HMS.Repository.DoctorRepository;
import in.HMS.Response.ApiResponse;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AppointmentRepository appointmentRepo;
    private final DoctorRepository doctorRepo;

    public AdminController(AppointmentRepository appointmentRepo,
                           DoctorRepository doctorRepo) {
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
    }

    // 1️⃣ View NEW appointments
    @GetMapping("/appointments/new")
    public ApiResponse<List<Appointment>> getNewAppointments() {
        return new ApiResponse<>(
                "success",
                "New appointments",
                appointmentRepo.findByStatus("NEW")
        );
    }

    // 2️⃣ Allocate appointment to doctor
    @PostMapping("/appointments/allocate")
    public ApiResponse<?> allocateDoctor(
            @RequestParam Integer appointmentId,
            @RequestParam Integer doctorId) {

        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() ->
                        new AdminException("Appointment not found", HttpStatus.NOT_FOUND));

        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() ->
                        new AdminException("Doctor not found", HttpStatus.NOT_FOUND));

        if (doctor.getIsBusy()) {
            appointment.setStatus("PENDING");
        } else {
            appointment.setDoctor(doctor);
            appointment.setStatus("ACTIVE");
            doctor.setIsBusy(true);
            doctorRepo.save(doctor);
        }

        appointmentRepo.save(appointment);

        return new ApiResponse<>("success", "Doctor allocation processed", null);
    }

    // 3️ View PENDING appointments
    @GetMapping("/appointments/pending")
    public ApiResponse<List<Appointment>> getPendingAppointments() {
        return new ApiResponse<>(
                "success",
                "Pending appointments",
                appointmentRepo.findByStatus("PENDING")
        );
    }

    // 4️ View COMPLETED appointments
    @GetMapping("/appointments/completed")
    public ApiResponse<List<Appointment>> getCompletedAppointments() {
        return new ApiResponse<>(
                "success",
                "Completed appointments",
                appointmentRepo.findByStatus("COMPLETED")
        );
    }
}
