package com.englishforadmin.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.englishforadmin.myconnection.MySQLconnection;
import model.Speaking;

public class SpeakingDAO {
    final public static String SELECT_ALL_SPEAKING_IN_LESSON_QUERY =
            "SELECT s.IdSpeaking, s.Title, s.Content, s.Example\n" +
                    "FROM SPEAKING s\n" +
                    "INNER JOIN SPEAKINGPART sp ON s.IdSpeaking = sp.IdSpeaking\n" +
                    "INNER JOIN LESSONPART lp ON sp.IdLessonPart = lp.IdLessonPart\n" +
                    "INNER JOIN LESSON l ON lp.IdLesson = l.IdLesson\n" +
                    "WHERE l.IdLesson = ?;";

    private final static String INSERT_SPEAKING_QUERY =
            "INSERT INTO SPEAKING (Title, Content, Example) " +
                    "VALUES (?,?,?);";

    private final static String UPDATE_SPEAKING_QUERY =
            "UPDATE SPEAKING \n" +
                    "SET Title = ?, Content = ?, Example = ?\n" +
                    "WHERE IdSpeaking = ?;";
    private final static String SELECT_LATEST_ID_SPEAKING_QUERY =
            "SELECT IdSpeaking\n" +
                    "FROM SPEAKING\n" +
                    "ORDER BY IdSpeaking DESC\n" +
                    "LIMIT 1;";
    public boolean insert(Speaking entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SPEAKING_QUERY)) {
                preparedStatement.setString(1, entity.getTitle());
                preparedStatement.setString(2, entity.getContent());
                preparedStatement.setBytes(3, entity.getExample());

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

    public boolean update(Speaking entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SPEAKING_QUERY)) {
                preparedStatement.setString(1, entity.getTitle());
                preparedStatement.setString(2, entity.getContent());
                preparedStatement.setBytes(3, entity.getExample());
                preparedStatement.setString(4, entity.getIdSpeaking());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Update successful.");
                    return true;
                } else {
                    System.out.println("Update failed.");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void delete(String id){

    }

    public Speaking selectById(String id){
        return null;
    }

    public List<Speaking> selectAll(){
        return null;
    }

    public List<Speaking> selectBySql(String sql, Object...args){
        LinkedList<Speaking> lstSpeaking = new LinkedList<>();
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Speaking speaking = new Speaking();
                    speaking.setIdSpeaking(resultSet.getString("IdSpeaking"));
                    speaking.setTitle(resultSet.getString("Title"));
                    speaking.setContent(resultSet.getString("Content"));
                    speaking.setExample(resultSet.getBytes("Example"));
                    lstSpeaking.add(speaking);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lstSpeaking;
    }

    public String getLastestId(){
        Connection connection = MySQLconnection.getConnection();
        String id = "";
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LATEST_ID_SPEAKING_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    id = resultSet.getString("IdSpeaking");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (id.isEmpty())
            id = "spe0000001";
        return id;
    }
}
