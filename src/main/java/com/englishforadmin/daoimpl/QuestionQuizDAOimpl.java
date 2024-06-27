package com.englishforadmin.daoimpl;

import com.englishforadmin.dao.QuestionQuizDAO;
import com.englishforadmin.myconnection.MySQLconnection;
import model.AnswerQuiz;
import model.QuestionQuiz;
import model.Quiz;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionQuizDAOimpl implements QuestionQuizDAO {
    private Connection connection;

    // Constructor
    public QuestionQuizDAOimpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<QuestionQuiz> getAllQuestionByQuizID(String quizID) throws SQLException {

        AnswerQuizDAOimpl answerQuizDAOimpl = new AnswerQuizDAOimpl(connection);
        List<QuestionQuiz> questionQuizzes = new ArrayList<>();
        String sql = "SELECT * FROM questionquiz WHERE IdQuiz = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, quizID);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    String idQuestionQuiz = resultSet.getString("IdQuestionQuiz");
                    String content = resultSet.getString("Content");
                    int serial = resultSet.getInt("Serial");
                    byte[] image = resultSet.getBytes("Image");
                    if ( idQuestionQuiz != null) {
                        List<AnswerQuiz> answerQuizList = answerQuizDAOimpl.getAllAnswerByQuestionQuizID(idQuestionQuiz);
                        QuestionQuiz questionQuiz = new QuestionQuiz(idQuestionQuiz, content, serial, image, quizID, answerQuizList);
                        questionQuizzes.add(questionQuiz);
                    }
                }
            }
        }


        return questionQuizzes;

    }
    @Override
    public boolean updateQuestionQuiz(QuestionQuiz questionQuiz) throws SQLException {
        String sql = "UPDATE questionquiz SET Content = ?, Serial = ?, Image = ? WHERE IdQuestionQuiz = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, questionQuiz.getContent());
            statement.setInt(2, questionQuiz.getSerial());
            statement.setBytes(3, questionQuiz.getImage());
            statement.setString(4, questionQuiz.getIdQuestionQuiz());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    @Override
    public String generateUniqueQuestionId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timestamp = now.format(formatter);
        String idQuestionQuiz = "Q" + timestamp.substring(0, 9);
        return idQuestionQuiz;
    }


    @Override
    public String  createQuestion(String content, int serial, String idQuiz, byte[] imageData) {
        String idQuestionQuiz = generateUniqueQuestionId();

        // Chuyển đổi giá trị char
        String idQuestionQuizChar = convertToChar(idQuestionQuiz);

        String sql = "INSERT INTO questionquiz (IdQuestionQuiz, Content, Serial, IdQuiz, Image) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idQuestionQuizChar);
            statement.setString(2, content);
            statement.setInt(3, serial);
            statement.setString(4, idQuiz);
            statement.setBytes(5, imageData);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new question was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idQuestionQuizChar;
    }


    @Override
    public String convertToChar(String input) {

        char[] charArray = new char[10];
        char[] inputChars = input.toCharArray();
        int length = Math.min(inputChars.length, charArray.length);
        System.arraycopy(inputChars, 0, charArray, 0, length);
        return new String(charArray);
    }
    @Override
    public String convertToString(String input) {
        // Chuyển đổi mảng char thành String
        return new String(input);
    }

}
