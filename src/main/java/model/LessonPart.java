package model;

public class LessonPart {
    private String idLessonPart;
    private String name;
    private String content;
    private LessonPartType type;
    private String idLesson;

    public enum LessonPartType {
        VOCABULARY,
        LISTENING,
        SPEAKING,
        QUIZ,
        GRAMMAR
    }

    public String getIdLessonPart() {
        return idLessonPart;
    }

    public void setIdLessonPart(String idLessonPart) {
        this.idLessonPart = idLessonPart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LessonPartType getType() {
        return type;
    }

    public void setType(LessonPartType type) {
        this.type = type;
    }

    public String getIdLesson() {
        return idLesson;
    }

    public void setIdLesson(String idLesson) {
        this.idLesson = idLesson;
    }
}
