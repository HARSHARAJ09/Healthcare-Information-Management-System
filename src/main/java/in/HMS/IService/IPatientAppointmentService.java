package in.HMS.IService;

import java.util.List;

import in.HMS.Entity.Appointment;

public interface IPatientAppointmentService {

    List<Appointment> getPatientAppointments(Integer patientId);

    Appointment getLatestCompletedAppointment(Integer patientId);
}
