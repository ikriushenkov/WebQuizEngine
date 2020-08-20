package engine.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.users.MyUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CompletedQuestion implements Comparable<CompletedQuestion> {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonProperty("id")
    private int idQuestion;

    private LocalDateTime completedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "my_user_id")
    private MyUser scholar;

    public CompletedQuestion() {
        completedAt = LocalDateTime.now();
    }

    public CompletedQuestion(int idQuestion, MyUser scholar) {
        this.idQuestion = idQuestion;
        completedAt = LocalDateTime.now();
        this.scholar = scholar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public int compareTo(CompletedQuestion o) {
        return getCompletedAt().compareTo(o.getCompletedAt());
    }
}
