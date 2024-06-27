package model;

public class AnswerQuiz {
    private String idAnswerQuiz;
    private String content;
    private boolean isCorrect;
    private String idQuestionQuiz;

    public AnswerQuiz(String idAnswerQuiz, String content, boolean isCorrect, String questionQuizID) {
        this.idAnswerQuiz = idAnswerQuiz;
        this.content = content;
        this.isCorrect = isCorrect;
        this.idQuestionQuiz = questionQuizID;
    }

    public String getIdAnswerQuiz() {
        return idAnswerQuiz;
    }

    public void setIdAnswerQuiz(String idAnswerQuiz) {
        this.idAnswerQuiz = idAnswerQuiz;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getIdQuestionQuiz() {
        return idQuestionQuiz;
    }

    public void setIdQuestionQuiz(String idQuestionQuiz) {
        this.idQuestionQuiz = idQuestionQuiz;
    }
}

