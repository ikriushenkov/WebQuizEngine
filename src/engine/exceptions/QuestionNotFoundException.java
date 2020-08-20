package engine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class QuestionNotFoundException extends ResponseStatusException {
    public QuestionNotFoundException(int id) {
        super(HttpStatus.NOT_FOUND, String.format("Question number %d does not exist", id));
    }

}
