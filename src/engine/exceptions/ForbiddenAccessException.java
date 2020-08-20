package engine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ForbiddenAccessException extends ResponseStatusException {
    public ForbiddenAccessException() {
        super(HttpStatus.FORBIDDEN, "You are not the owner of this question.");
    }
}
