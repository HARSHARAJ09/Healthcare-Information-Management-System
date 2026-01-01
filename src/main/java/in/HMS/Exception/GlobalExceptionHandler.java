package in.HMS.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AdminException.class)
    public ResponseEntity<Map<String, Object>> handleAdminException(AdminException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("status", "failure");
        error.put("type", "Admin Exception");
        error.put("error", ex.getMessage());
        error.put("localTime", LocalDateTime.now());

        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    @ExceptionHandler(DoctorException.class)
    public ResponseEntity<Map<String, Object>> handleDoctorException(DoctorException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("status", "failure");
        error.put("type", "Doctor Exception");
        error.put("error", ex.getMessage());
        error.put("localTime", LocalDateTime.now());

        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    @ExceptionHandler(PatientException.class)
    public ResponseEntity<Map<String, Object>> handlePatientException(PatientException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("status", "failure");
        error.put("type", "Patient Exception");
        error.put("error", ex.getMessage());
        error.put("localTime", LocalDateTime.now());

        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, Object>> handleAuthException(AuthException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("status", "failure");
        error.put("type", "Auth Exception");
        error.put("error", ex.getMessage());
        error.put("localTime", LocalDateTime.now());

        return new ResponseEntity<>(error, ex.getHttpStatus());
    }
}

