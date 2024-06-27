package com.englishforadmin.daoimpl;

import com.englishforadmin.myconnection.MySQLconnection;
import model.ListeningPart;
import model.VocabularyPart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VocabularyPartDAO {
    private final static String INSERT_VOCABULARYPART_QUERY =
            "INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart) " +
                    "VALUES (?,?)";
    public boolean insert(VocabularyPart entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VOCABULARYPART_QUERY)) {
                preparedStatement.setString(1, entity.getIdVocabulary());
                preparedStatement.setString(2, entity.getIdLessonPart());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Insertion successful.");
                    return true;
                } else {
                    System.out.println("Insertion failed.");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
