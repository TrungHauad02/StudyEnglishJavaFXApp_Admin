package com.englishforadmin;

public class DataManager {
    private static DataManager instance;
    private String quizQuestionNumber;

    private DataManager() {}

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public String getQuizQuestionNumber() {
        return quizQuestionNumber;
    }

    public void setQuizQuestionNumber(String quizQuestionNumber) {
        this.quizQuestionNumber = quizQuestionNumber;
    }
}
