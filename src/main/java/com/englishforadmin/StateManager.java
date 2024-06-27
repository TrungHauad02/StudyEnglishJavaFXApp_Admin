package com.englishforadmin;

import model.Lesson;
import model.QuestionQuiz;
import model.Quiz;
import model.Vocabulary;

public class StateManager {
    private static Lesson currentLesson;
    private static Quiz currentQuiz;
    private static QuestionQuiz currentQuestion;

    private static String questionID;

    private  static Vocabulary currentVocabulary;


    public static String getQuestionID() {
        return questionID;
    }

    public static void setQuestionID(String questionID) {
        StateManager.questionID = questionID;
    }

    public static void setCurrentLesson(Lesson lesson) {
        currentLesson = lesson;
    }

    public static Lesson getCurrentLesson() {
        return currentLesson;
    }
    public static void setCurrentQuiz(Quiz quiz) {
        currentQuiz = quiz;
    }

    public static Quiz getCurrentQuiz() {
        return currentQuiz;
    }
    public static void setCurrentQuestion(QuestionQuiz question) {
        currentQuestion = question;
    }

    public static QuestionQuiz getCurrentQuestion() {
        return currentQuestion;
    }

    public static Vocabulary getCurrentVocabulary() {
        return currentVocabulary;
    }

    public static void setCurrentVocabulary(Vocabulary currentVocabulary) {
        StateManager.currentVocabulary = currentVocabulary;
    }
}
