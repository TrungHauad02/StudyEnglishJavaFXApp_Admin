package model;

import java.util.List;

public class Quiz {
    private String idQuiz;
    private String title;
    private QuizStatus status;

    public enum QuizStatus {

        lock,
        unlock,
        hidden

    }

    public String getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(String idQuiz) {
        this.idQuiz = idQuiz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuizStatus getStatus() {
        return status;
    }

    public void setStatus(QuizStatus status) {
        this.status = status;
    }
}

