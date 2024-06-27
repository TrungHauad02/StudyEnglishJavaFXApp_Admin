package com.englishforadmin.dao;

import model.AnswerQuiz;

import java.sql.SQLException;
import java.util.List;

public interface AnswerQuizDAO {
    List<AnswerQuiz> getAllAnswerByQuestionQuizID ( String questionQuizID) throws SQLException;
    boolean addNewAnswer(String content, boolean isCorrect, String idQuestionQuiz) throws SQLException;
    String convertToChar(String input);
    String generateUniqueAnswerId();
    boolean editAnswer(String idAnswerQuiz, String content, boolean isCorrect, String idQuestionQuiz);


}
