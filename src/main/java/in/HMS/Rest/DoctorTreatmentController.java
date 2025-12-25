package in.HMS.Rest;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import in.HMS.Entity.Doctor;
import in.HMS.Entity.User;
import in.HMS.Exception.DoctorException;
import in.HMS.IService.IDoctorService;
import in.HMS.IService.IDoctorTreatmentService;
import in.HMS.Request.DoctorPrescriptionRequest;
import in.HMS.Response.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctor")
public class DoctorTreatmentController {

    private final IDoctorService doctorService;
    private final IDoctorTreatmentService treatmentService;

    public DoctorTreatmentController(
            IDoctorService doctorService,
            IDoctorTreatmentService treatmentService) {
        this.doctorService = doctorService;
        this.treatmentService = treatmentService;
    }

    // 🔐 COMPLETE TREATMENT
    @PostMapping("/treatment/complete")
    public ApiResponse<?> completeTreatment(
            @Valid @RequestBody DoctorPrescriptionRequest request,
            Authentication authentication) {

        Integer userId = ((User) authentication.getPrincipal()).getUserId();

        Doctor doctor = doctorService.findByUserId(userId);
        if (doctor == null) {
            throw new DoctorException("Doctor not found", null);
        }

        treatmentService.completeTreatment(
                doctor.getDoctorId(),
                request.getPrescription());

        return new ApiResponse<>(
                "success",
                "Treatment completed successfully",
                null
        );
    }
}
