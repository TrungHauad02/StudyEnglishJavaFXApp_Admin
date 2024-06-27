package com.englishforadmin.daoimpl;

import com.englishforadmin.myconnection.MySQLconnection;
import model.GrammarPart;
import model.LessonPart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GrammarPartDAO {
    private final static String INSERT_GRAMMARPART_QUERY =
            "INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart) " +
                    "VALUES (?,?)";
    public boolean insert(GrammarPart entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GRAMMARPART_QUERY)) {
                preparedStatement.setString(1, entity.getIdGrammar());
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
