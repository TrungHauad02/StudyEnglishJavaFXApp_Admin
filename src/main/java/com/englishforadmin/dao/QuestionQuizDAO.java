package com.englishforadmin.dao;

import model.AnswerQuiz;
import model.Lesson;
import model.QuestionQuiz;
import model.Quiz;

import java.sql.SQLException;
import java.util.List;

public interface QuestionQuizDAO {
    List<QuestionQuiz> getAllQuestionByQuizID(String quizID) throws SQLException;

    boolean updateQuestionQuiz(QuestionQuiz questionQuiz) throws SQLException;

    String generateUniqueQuestionId();

    String createQuestion(String content, int serial, String idQuiz, byte[] imageData);

    String convertToChar(String input);

    String convertToString(String input);


}
