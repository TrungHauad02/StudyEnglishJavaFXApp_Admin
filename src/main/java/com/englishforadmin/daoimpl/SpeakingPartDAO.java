package com.englishforadmin.daoimpl;

import com.englishforadmin.myconnection.MySQLconnection;
import model.ListeningPart;
import model.SpeakingPart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SpeakingPartDAO {
    private final static String INSERT_SPEAKINGPART_QUERY =
            "INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart) " +
                    "VALUES (?,?)";
    public boolean insert(SpeakingPart entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SPEAKINGPART_QUERY)) {
                preparedStatement.setString(1, entity.getIdSpeaking());
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
