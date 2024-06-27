package model;

public class SubmitLesson {
    private String idSubmitLesson;
    private String idUser;
    private String idLesson;
    private SubmitStatus status;

    public enum SubmitStatus {
        UNCOMPLETE,
        COMPLETE
    }

    public String getIdSubmitLesson() {
        return idSubmitLesson;
    }

    public void setIdSubmitLesson(String idSubmitLesson) {
        this.idSubmitLesson = idSubmitLesson;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdLesson() {
        return idLesson;
    }

    public void setIdLesson(String idLesson) {
        this.idLesson = idLesson;
    }

    public SubmitStatus getStatus() {
        return status;
    }

    public void setStatus(SubmitStatus status) {
        this.status = status;
    }
}