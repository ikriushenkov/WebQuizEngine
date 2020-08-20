package engine.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.users.MyUser;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "The title must not be empty")
    private String title;

    @NotBlank(message = "The text must not be empty")
    private String text;

    @ElementCollection
    @NotNull
    @Size(min = 2, message = "Minimum 2 answers")
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    private List<Integer> answer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "my_user_id")
    private MyUser owner;

    public Question() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return Objects.requireNonNullElseGet(answer, ArrayList::new);
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public MyUser getOwner() {
        return owner;
    }

    public void setOwner(MyUser owner) {
        this.owner = owner;
    }
}
