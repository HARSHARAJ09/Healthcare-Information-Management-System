package in.HMS.IService.Imp;

import java.util.List;

import org.springframework.stereotype.Service;

import in.HMS.Entity.Appointment;
import in.HMS.IService.IPatientAppointmentService;
import in.HMS.Repository.AppointmentRepo;

@Service
public class PatientAppointmentServiceImp implements IPatientAppointmentService {

    private final AppointmentRepo appointmentRepository;

    public PatientAppointmentServiceImp(AppointmentRepo appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Appointment> getPatientAppointments(Integer patientId) {
        return appointmentRepository.findByPatient_PatientId(patientId);
    }

    @Override
    public Appointment getLatestCompletedAppointment(Integer patientId) {
        return appointmentRepository
                .findTopByPatient_PatientIdAndStatusOrderByCompletedAtDesc(
                        patientId, "COMPLETED");
    }
}
