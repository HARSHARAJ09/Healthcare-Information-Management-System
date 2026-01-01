package in.HMS.IService;

import java.util.List;

import in.HMS.Entity.Appointment;

public interface IAppointmentService {

    Appointment create(Appointment appointment);

    Appointment findById(Long appointmentId);

    List<Appointment> findByStatus(String status);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

    Appointment update(Appointment appointment);
}
