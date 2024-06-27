package com.englishforadmin.daoimpl;

import com.englishforadmin.myconnection.MySQLconnection;
import model.Grammar;
import model.GrammarPart;
import model.LessonPart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonPartDAO {
    private final static String INSERT_LESSONPART_QUERY =
            "INSERT INTO LESSONPART (Type, IdLesson) " +
                    "VALUES (?,?)";
    private final static String SELECT_LATEST_ID_LESSONPART_QUERY =
            "SELECT IdLessonPart\n" +
                    "FROM LESSONPART\n" +
                    "ORDER BY IdLessonPart DESC\n" +
                    "LIMIT 1;";
    public boolean insert(LessonPart entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LESSONPART_QUERY)) {
                preparedStatement.setString(1, entity.getType().toString().toLowerCase());
                preparedStatement.setString(2, entity.getIdLesson());

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

    public String getLastestId(){
        Connection connection = MySQLconnection.getConnection();
        String id = "";
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LATEST_ID_LESSONPART_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    id = resultSet.getString("IdLessonPart");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (id.isEmpty())
            id = "Lpart00001";
        return id;
    }
}
