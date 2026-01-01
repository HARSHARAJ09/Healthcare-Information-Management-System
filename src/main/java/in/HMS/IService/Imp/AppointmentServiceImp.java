package in.HMS.IService.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.HMS.Entity.Appointment;
import in.HMS.IService.IAppointmentService;
import in.HMS.Repository.AppointmentRepository;


@Service
public class AppointmentServiceImp implements IAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment create(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment findById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
    }

    @Override
    public List<Appointment> findByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }

    @Override
    public List<Appointment> findByPatientId(Long patientId) {
        return appointmentRepository.findByPatientPatientId(patientId);
    }

    @Override
    public List<Appointment> findByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorDoctorId(doctorId);
    }

    @Override
    public Appointment update(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
}
