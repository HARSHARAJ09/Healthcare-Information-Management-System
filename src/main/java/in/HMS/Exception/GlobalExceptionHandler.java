package in.HMS.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // USER EXCEPTION
    @ExceptionHandler(UserException.class)
    public ResponseEntity<Map<String, Object>> handleUserException(UserException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "failure");
        response.put("type", "USER_EXCEPTION");
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, ex.getStatus());
    }

    // ADMIN EXCEPTION
    @ExceptionHandler(AdminException.class)
    public ResponseEntity<Map<String, Object>> handleAdminException(AdminException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "failure");
        response.put("type", "ADMIN_EXCEPTION");
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(PatientException.class)
    public ResponseEntity<Map<String, Object>> handlePatientException(PatientException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "failure");
        response.put("type", "PATIENT_EXCEPTION");
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, ex.getStatus());
    }
    
    @ExceptionHandler(DoctorException.class)
    public ResponseEntity<Map<String, Object>> handleDoctorException(DoctorException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "failure");
        response.put("type", "DOCTOR_EXCEPTION");
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, ex.getStatus());
    }

    // FALLBACK (ANY UNHANDLED ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "failure");
        response.put("type", "INTERNAL_ERROR");
        response.put("message", "Something went wrong");
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    


}
