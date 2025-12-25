package in.HMS.Exception;

import org.springframework.http.HttpStatus;

public class DoctorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    public DoctorException(String message, HttpStatus status) {
        super(message);
        this.status = (status != null)
                ? status
                : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
