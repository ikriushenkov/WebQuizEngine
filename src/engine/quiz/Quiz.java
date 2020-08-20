package engine.quiz;

import engine.exceptions.ForbiddenAccessException;
import engine.exceptions.QuestionNotFoundException;
import engine.users.MyUserPrincipal;
import engine.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RequestMapping(path = "/api/quizzes")
@RestController
public class Quiz {

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private CompletedQuestionRepository completedQuestions;

    @Autowired
    private UserRepository users;

    public Quiz() {
    }

    private Question getQuestion(int id) {
        return questions.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }

    @GetMapping(path = "/{id}")
    public Question getQuestionById(@PathVariable int id) {
        return getQuestion(id);
    }

    @GetMapping
    public Page<Question> getAllQuestions(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int pageSize) {
        return questions.findAll(PageRequest.of(page, pageSize));
    }

    @PostMapping(path = "/{id}/solve", consumes = "application/json")
    public Reply getAnswer(@PathVariable int id, @RequestBody Answer answer,
                           @AuthenticationPrincipal MyUserPrincipal user) {
        if (Arrays.equals(getQuestion(id).getAnswer().toArray(), answer.getAnswer().toArray())) {
            completedQuestions.save(new CompletedQuestion(id, user.getUser()));
            return Reply.WIN;
        } else {
            return Reply.LOSE;
        }
    }

    @PostMapping(consumes = "application/json")
    public Question addQuestion(@Valid @RequestBody Question question,
                                @AuthenticationPrincipal MyUserPrincipal user) {
        question.setOwner(user.getUser());
        return questions.save(question);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deleteQuestion(@PathVariable int id, @AuthenticationPrincipal MyUserPrincipal user) {
        Question res = getQuestion(id);
        if (Objects.equals(res.getOwner().getUsername(), user.getUsername())) {
            questions.delete(res);
        } else {
            throw new ForbiddenAccessException();
        }
    }

    @GetMapping(path = "/completed")
    public Page<CompletedQuestion> getCompleted(@AuthenticationPrincipal MyUserPrincipal user,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int pageSize) {
        List <CompletedQuestion> res = users.findByUsername(user.getUsername()).
                getCompletedQuestions();
        res.sort(Comparator.reverseOrder());
        return new PageImpl<>(res.subList(page * pageSize, Math.min((page + 1) * pageSize,
                res.size())), PageRequest.of(page, pageSize), res.size());
    }
}
