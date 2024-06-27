package com.englishforadmin.daoimpl;

import com.englishforadmin.myconnection.MySQLconnection;
import model.GrammarPart;
import model.Listening;
import model.ListeningPart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ListeningPartDAO {
    private final static String INSERT_LISTENINGPART_QUERY =
            "INSERT INTO LISTENINGPART (IdListening, IdLessonPart) " +
                    "VALUES (?,?)";
    public boolean insert(ListeningPart entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LISTENINGPART_QUERY)) {
                preparedStatement.setString(1, entity.getIdListening());
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
