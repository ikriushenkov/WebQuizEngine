package engine.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.quiz.CompletedQuestion;
import engine.quiz.Question;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty("email")
    @Email(regexp = ".+@.+\\..+")
    @Column(nullable = false, unique = true)
    private String username;

    @Size(min = 5)
    @Column
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Question> questions;

    @JsonIgnore
    @OneToMany(mappedBy = "scholar")
    private List<CompletedQuestion> completedQuestions;

    public MyUser() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<CompletedQuestion> getCompletedQuestions() {
        return completedQuestions;
    }

    public void setCompletedQuestions(List<CompletedQuestion> completedQuestions) {
        this.completedQuestions = completedQuestions;
    }
}
