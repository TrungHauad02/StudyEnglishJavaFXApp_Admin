package com.englishforadmin.daoimpl;

import com.englishforadmin.dao.LessonDAO;
import com.englishforadmin.myconnection.MySQLconnection;
import model.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDAOimpl implements LessonDAO {
    private Connection connection ;
    private final static String INSERT_LESSON_QUERY =
            "INSERT INTO LESSON (Name, Description, Status, Serial)" +
                    "VALUES (?,?,?,?)";
    private final static String SELECT_LATEST_SERIAL_LESSON_QUERY =
            "SELECT MAX(Serial) AS MaxSerial FROM LESSON;\n";
    private final static String SELECT_SERIAL_LESSON_QUERY =
            "SELECT Serial FROM LESSON WHERE Serial = ?;";
    private final static String SELECT_LATEST_ID_LESSON_QUERY =
            "SELECT IdLesson\n" +
                    "FROM LESSON\n" +
                    "ORDER BY IdLesson DESC\n" +
                    "LIMIT 1;";
    private final static String UPDATE_LESSON_QUERY =
            "UPDATE LESSON\n" +
            "SET Name = ?, Description = ?, Status = ?, Serial = ?\n" +
            "WHERE IdLesson = ?;";
    public LessonDAOimpl(Connection connection) {
        this.connection = connection;
     }
    public LessonDAOimpl(){}

    @Override
    public List<Lesson> getAllLessons() throws SQLException {
        List<Lesson> lessons = new ArrayList<>();

        String query = "SELECT l.IdLesson ,l.Serial , l.Name, l.Status FROM Lesson l";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Lesson lesson = new Lesson();

                lesson.setIdLesson(resultSet.getString("IdLesson"));
                lesson.setSerial(resultSet.getInt("Serial"));
                lesson.setName(resultSet.getString("Name"));
                lesson.setStatus(Lesson.LessonStatus.valueOf(resultSet.getString("Status").toLowerCase()));

                lessons.add(lesson);
            }
        }

        return lessons;
    }

    @Override
    public Lesson getLessonById(String lessonId) {
        // Kết nối với cơ sở dữ liệu
        Connection connection = MySQLconnection.getConnection();
        Lesson lesson = null;

        try {
            // Tạo truy vấn SQL để lấy bài học với idLesson tương ứng
            String sql = "SELECT * FROM lesson WHERE IdLesson = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, lessonId);

            // Thực hiện truy vấn
            ResultSet resultSet = statement.executeQuery();

            // Nếu có kết quả từ truy vấn
            if (resultSet.next()) {
                // Tạo một đối tượng Lesson mới và gán giá trị từ kết quả của truy vấn
                lesson = new Lesson();
                lesson.setIdLesson(resultSet.getString("IdLesson"));
                lesson.setName(resultSet.getString("Name"));
                lesson.setDescription(resultSet.getString("Description"));
                lesson.setStatus(Lesson.LessonStatus.valueOf(resultSet.getString("Status").toLowerCase()));
                lesson.setSerial(resultSet.getInt("Serial"));
            }

            // Đóng các tài nguyên
            //resultSet.close();
            //tatement.close();
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lesson;
    }

    public boolean insert(Lesson entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LESSON_QUERY)) {
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getDescription());
                preparedStatement.setString(3, entity.getStatus().toString().toLowerCase());
                preparedStatement.setInt(4, entity.getSerial());
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

    public boolean update(Lesson entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LESSON_QUERY)) {
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getDescription());
                preparedStatement.setString(3, entity.getStatus().toString().toLowerCase());
                preparedStatement.setInt(4, entity.getSerial());
                preparedStatement.setString(5, entity.getIdLesson());

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
                return false;
            }
        }
        return false;
    }

    public int getLastestSerial(){
        Connection connection = MySQLconnection.getConnection();
        int serial = 1;
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LATEST_SERIAL_LESSON_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    serial = resultSet.getInt("MaxSerial");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return serial;
    }

    public boolean checkDuplicateSerial(int serial){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SERIAL_LESSON_QUERY)) {
                preparedStatement.setInt(1, serial);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    return true;
                }else {
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
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LATEST_ID_LESSON_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    id = resultSet.getString("IdLesson");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (id.isEmpty())
            id = "Less000001";
        return id;
    }
}