package in.HMS.IService;

import in.HMS.Entity.Appointment;

public interface IDoctorAppointmentService {

    Appointment getActiveAppointment(Integer doctorId);
}
