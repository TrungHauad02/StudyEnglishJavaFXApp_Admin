package com.englishforadmin.daoimpl;

import com.englishforadmin.myconnection.MySQLconnection;
import model.Listening;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ListeningDAO{
    public static final String SELECT_LISTENING_FROM_LESSON =
            "SELECT L.IdListening, L.Title, L.Description, L.Video, L.Script\n" +
                    "FROM LISTENING L\n" +
                    "JOIN LISTENINGPART LP ON L.IdListening = LP.IdListening\n" +
                    "JOIN LESSONPART LP2 ON LP.IdLessonPart = LP2.IdLessonPart\n" +
                    "WHERE LP2.IdLesson = ?;\n";
    private final static String INSERT_LISTENING_QUERY =
            "INSERT INTO LISTENING (Title, Description, Script, Video) " +
                    "VALUES (?,?,?,?);";

    private final static String UPDATE_LISTENING_QUERY =
            "UPDATE LISTENING \n" +
            "SET Title = ?, Description = ?, Script = ?, Video = ?\n" +
            "WHERE IdListening = ?;";

    private final static String SELECT_LATEST_ID_LISTENING_QUERY =
            "SELECT IdListening\n" +
            "FROM LISTENING\n" +
            "ORDER BY IdListening DESC\n" +
            "LIMIT 1;";
    public boolean insert(Listening entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LISTENING_QUERY)) {
                preparedStatement.setString(1, entity.getTitle());
                preparedStatement.setString(2, entity.getDescription());
                preparedStatement.setString(3, entity.getScript());
                preparedStatement.setBytes(4, entity.getVideo());

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

    public boolean update(Listening entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LISTENING_QUERY)) {
                preparedStatement.setString(1, entity.getTitle());
                preparedStatement.setString(2, entity.getDescription());
                preparedStatement.setString(3, entity.getScript());
                preparedStatement.setBytes(4, entity.getVideo());
                preparedStatement.setString(5, entity.getIdListening());

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
    public Listening selectById(String id){
        return null;
    }

    public List<Listening> selectAll(){
        return null;
    }
    public List<Listening> selectBySql(String sql, Object...args) {
        List<Listening> lstListening = new LinkedList<>();
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Listening listening = new Listening();
                    listening.setIdListening(resultSet.getString("IdListening"));
                    listening.setTitle(resultSet.getString("Title"));
                    listening.setDescription(resultSet.getString("Description"));
                    listening.setScript(resultSet.getString("Script"));
                    listening.setVideo(resultSet.getBytes("Video"));
                    lstListening.add(listening);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lstListening;
    }

    public String getLastestId(){
        Connection connection = MySQLconnection.getConnection();
        String id = "";
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LATEST_ID_LISTENING_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    id = resultSet.getString("IdListening");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (id.isEmpty())
            id = "List000001";
        return id;
    }
}
