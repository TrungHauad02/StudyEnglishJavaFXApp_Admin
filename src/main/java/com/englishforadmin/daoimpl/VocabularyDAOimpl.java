package com.englishforadmin.daoimpl;

import com.englishforadmin.dao.VocabularyDAO;
import model.Quiz;
import model.Vocabulary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VocabularyDAOimpl implements VocabularyDAO {
    private Connection connection;
    public VocabularyDAOimpl (Connection connection)
    {
        this.connection = connection;
    }
    @Override
    public List<Vocabulary> getAllVocabularys() throws SQLException {
        try {
            List<Vocabulary> vocabulary = new ArrayList<>();

            String query = "SELECT q.IdVocabulary, q.Word,q.Mean, q.Phonetic FROM Vocabulary q";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Vocabulary vocabulary1 = new Vocabulary();
                    vocabulary1.setIdVocabulary(resultSet.getString("IdVocabulary"));
                    vocabulary1.setWord(resultSet.getString("Word"));
                    vocabulary1.setMean(resultSet.getString("Mean"));
                    vocabulary1.setPhonetic(resultSet.getString("Phonetic"));

                    vocabulary.add(vocabulary1);
                }
            }
            return vocabulary;
        } catch (SQLException ex) {
            // In ra lỗi và throw lại exception
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public Vocabulary getVocabularyById(String vocabularyID) throws SQLException {
        String query = "SELECT IdVocabulary, Word, Mean, Image, Phonetic FROM vocabulary WHERE IdVocabulary = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, vocabularyID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Vocabulary vocabulary = new Vocabulary();
                    vocabulary.setIdVocabulary(resultSet.getString("IdVocabulary"));
                    vocabulary.setWord(resultSet.getString("Word"));
                    vocabulary.setMean(resultSet.getString("Mean"));
                    vocabulary.setImage(resultSet.getBytes("Image"));
                    vocabulary.setPhonetic(resultSet.getString("Phonetic"));
                    return vocabulary;
                } else {
                    // Không tìm thấy từ vựng có id tương ứng
                    return null;
                }
            }
        } catch (SQLException ex) {
            // In ra lỗi và throw lại exception
            ex.printStackTrace();
            throw ex;
        }
    }
}
