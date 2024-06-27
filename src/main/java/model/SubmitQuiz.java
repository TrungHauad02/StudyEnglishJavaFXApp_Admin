package model;

import java.util.Date;

public class SubmitQuiz {
    private String idSubmitQuiz;
    private int score;
    private Date startTime;
    private Date endTime;
    private String idQuiz;
    private String idUser;

    public String getIdSubmitQuiz() {
        return idSubmitQuiz;
    }

    public void setIdSubmitQuiz(String idSubmitQuiz) {
        this.idSubmitQuiz = idSubmitQuiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(String idQuiz) {
        this.idQuiz = idQuiz;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
