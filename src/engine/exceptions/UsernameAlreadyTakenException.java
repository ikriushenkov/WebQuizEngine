package engine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameAlreadyTakenException extends ResponseStatusException {
    public UsernameAlreadyTakenException() {
        super(HttpStatus.BAD_REQUEST, "That username is taken. Try another.");
    }
}
