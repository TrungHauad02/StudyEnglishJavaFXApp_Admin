package model;

import java.util.Date;

public class Lesson {
    private String idLesson;
    private String name;
    private String description;
    private LessonStatus status;
    private int serial;
    public enum LessonStatus {
        lock,
        unlock,
        hidden
    }
    public Lesson() {
    }
    public Lesson(String idLesson, String name, String description, LessonStatus status, int serial) {
        this.idLesson = idLesson;
        this.name = name;
        this.description = description;
        this.status = status;
        this.serial = serial;
    }

    public String getIdLesson() {
        return idLesson;
    }

    public void setIdLesson(String idLesson) {
        this.idLesson = idLesson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LessonStatus getStatus() {
        return status;
    }

    public void setStatus(LessonStatus status) {
        this.status = status;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
}