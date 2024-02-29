package engine.DTO;

import engine.model.Quiz;

import java.util.List;

public class ResponseQuizDTO {

    private int id;
    private String title;
    private String text;
    private List<String> options;

    public ResponseQuizDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void loadQuiz(Quiz userQuiz) {
        this.id = userQuiz.getId();
        this.title = userQuiz.getTitle();
        this.text = userQuiz.getText();
        this.options = userQuiz.getOptions();
    }

}
