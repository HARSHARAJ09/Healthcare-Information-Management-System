package in.HMS.IService.Imp;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.HMS.Entity.Appointment;
import in.HMS.Entity.Doctor;
import in.HMS.Exception.DoctorException;
import in.HMS.IService.IDoctorTreatmentService;
import in.HMS.Repository.AppointmentRepository;
import in.HMS.Repository.DoctorRepository;

@Service
public class DoctorTreatmentServiceImp implements IDoctorTreatmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    public DoctorTreatmentServiceImp(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    @Transactional
    public void completeTreatment(Integer doctorId, String prescription) {

        Appointment appointment =
                appointmentRepository.findByDoctor_DoctorIdAndStatus(
                        doctorId, "ACTIVE");

        if (appointment == null) {
            throw new DoctorException(
                    "No active appointment found",
                    HttpStatus.NOT_FOUND);
        }

        // 1️⃣ Add prescription
        appointment.setPrescription(prescription);
        appointment.setStatus("COMPLETED");
        appointment.setCompletedAt(LocalDateTime.now());

        // 2️⃣ Free doctor
        Doctor doctor = appointment.getDoctor();
        doctor.setIsBusy(false);

        appointmentRepository.save(appointment);
        doctorRepository.save(doctor);
    }
}
