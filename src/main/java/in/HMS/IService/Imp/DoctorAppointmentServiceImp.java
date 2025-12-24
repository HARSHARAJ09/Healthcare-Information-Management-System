package in.HMS.IService.Imp;

import org.springframework.stereotype.Service;

import in.HMS.Entity.Appointment;
import in.HMS.IService.IDoctorAppointmentService;
import in.HMS.Repository.AppointmentRepository;

@Service
public class DoctorAppointmentServiceImp implements IDoctorAppointmentService {

    private final AppointmentRepository appointmentRepository;

    public DoctorAppointmentServiceImp(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment getActiveAppointment(Integer doctorId) {
        return appointmentRepository
                .findByDoctor_DoctorIdAndStatus(doctorId, "ACTIVE");
    }
}
