package in.HMS.Exception;

import org.springframework.http.HttpStatus;

public class AuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;

    public AuthException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus != null
                ? httpStatus
                : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

