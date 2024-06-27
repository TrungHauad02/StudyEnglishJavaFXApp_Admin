package com.englishforadmin.dao;

import model.Quiz;
import model.Vocabulary;

import java.sql.SQLException;
import java.util.List;

public interface VocabularyDAO {
    List<Vocabulary> getAllVocabularys() throws SQLException;

    Vocabulary getVocabularyById(String VocabularyID) throws SQLException ;

    //void addQuiz(Quiz quiz);

    //boolean updateVocabulary(Vocabulary vocabulary);

    //void deleteQuiz(int quizId);
}
