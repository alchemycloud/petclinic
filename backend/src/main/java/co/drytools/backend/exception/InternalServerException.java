package co.drytools.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {

    public InternalServerException(ExceptionType type) {
        super(type.toString());
    }

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(Exception e) {
        super(e);
    }
}
