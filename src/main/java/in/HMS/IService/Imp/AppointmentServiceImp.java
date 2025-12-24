package in.HMS.IService.Imp;

import org.springframework.stereotype.Service;

import in.HMS.Entity.Appointment;
import in.HMS.IService.IAppointmentService;
import in.HMS.Repository.AppointmentRepo;

@Service
public class AppointmentServiceImp implements IAppointmentService {

    private final AppointmentRepo appointmentRepository;

    public AppointmentServiceImp(AppointmentRepo appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
}
