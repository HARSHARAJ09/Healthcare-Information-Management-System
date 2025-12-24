package in.HMS.IService;

import in.HMS.Entity.Patient;

public interface IPatient {

    Patient registerPatient(Patient patient);

    Patient findByUserId(Integer userId);
}
