package com.englishforadmin.dao;

import model.Quiz;

import java.sql.SQLException;
import java.util.List;

public interface QuizDAO {
    List<Quiz> getAllQuizzes() throws SQLException;

    Quiz getQuizById(String quizId);

    boolean  addNewQuiz(Quiz quiz);

    boolean updateQuiz(Quiz quiz);

    boolean deleteQuiz(String idQuiz);
}