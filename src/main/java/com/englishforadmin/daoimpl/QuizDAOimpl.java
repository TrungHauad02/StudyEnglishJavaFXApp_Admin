package com.englishforadmin.daoimpl;

import com.englishforadmin.dao.QuizDAO;
import com.englishforadmin.myconnection.MySQLconnection;
import model.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAOimpl implements QuizDAO {
    private Connection connection;
    public QuizDAOimpl (Connection connection)
    {
        this.connection = connection;
    }
    @Override
    public List<Quiz> getAllQuizzes() throws SQLException {
        try {
            List<Quiz> quizzes = new ArrayList<>();

            String query = "SELECT q.IdQuiz, q.Title, q.Status FROM quiz q";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setIdQuiz(resultSet.getString("IdQuiz"));
                    quiz.setTitle(resultSet.getString("Title"));
                    quiz.setStatus(Quiz.QuizStatus.valueOf(resultSet.getString("Status").toLowerCase()));

                    quizzes.add(quiz);
                }
            }
            return quizzes;
        } catch (SQLException ex) {
            // In ra lỗi và throw lại exception
            ex.printStackTrace();
            throw ex;
        }
    }
    @Override
    public Quiz getQuizById(String quizId) {
        Connection connection = MySQLconnection.getConnection();
        Quiz quiz = null;

        try {
            String sql = "SELECT * FROM quiz WHERE IdQuiz = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, quizId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                quiz = new Quiz();
                quiz.setIdQuiz(resultSet.getString("IdQuiz"));
                quiz.setTitle(resultSet.getString("Title"));
                quiz.setStatus(Quiz.QuizStatus.valueOf(resultSet.getString("Status").toLowerCase()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quiz;
    }

    @Override
    public boolean updateQuiz(Quiz quiz) {
        Connection connection = MySQLconnection.getConnection();
        boolean result = false;

        try {
            String sql = "UPDATE quiz SET Title = ?, Status = ? WHERE IdQuiz = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, quiz.getTitle());
            statement.setString(2, quiz.getStatus().toString().toLowerCase());
            statement.setString(3, quiz.getIdQuiz());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    @Override
    public boolean addNewQuiz(Quiz quiz) {
        boolean result = false;
        Connection connection = MySQLconnection.getConnection();

        try {
            // SQL query for inserting a new quiz without specifying IdQuiz
            String sql = "INSERT INTO quiz (Title, Status) VALUES (?, ?)";

            // Create a PreparedStatement object for the SQL query
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set the parameters for the PreparedStatement
            statement.setString(1, quiz.getTitle());
            statement.setString(2, quiz.getStatus().toString().toLowerCase());

            // Execute the query to insert the new quiz
            int rowsInserted = statement.executeUpdate();

            // Check if the quiz was inserted successfully
            if (rowsInserted > 0) {
                result = true;
                System.out.println("A new quiz was added successfully.");
            } else {
                System.out.println("Failed to add the new quiz.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    @Override
    public boolean deleteQuiz(String idQuiz) {
        boolean result = false;
        Connection connection = MySQLconnection.getConnection();

        try {
            // SQL query for deleting a quiz by IdQuiz
            String sql = "DELETE FROM quiz WHERE IdQuiz = ?";

            // Create a PreparedStatement object for the SQL query
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set the parameter for the PreparedStatement
            statement.setString(1, idQuiz);

            // Execute the query to delete the quiz
            int rowsDeleted = statement.executeUpdate();

            // Check if the quiz was deleted successfully
            if (rowsDeleted > 0) {
                result = true;
                System.out.println("The quiz was deleted successfully.");
            } else {
                System.out.println("Failed to delete the quiz.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }




}
