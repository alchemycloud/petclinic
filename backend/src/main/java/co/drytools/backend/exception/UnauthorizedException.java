package co.drytools.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(ExceptionType type) {
        super(type.toString());
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
