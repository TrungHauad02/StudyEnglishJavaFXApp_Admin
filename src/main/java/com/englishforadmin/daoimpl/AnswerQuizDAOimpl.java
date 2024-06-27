package com.englishforadmin.daoimpl;

import com.englishforadmin.dao.AnswerQuizDAO;
import model.AnswerQuiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnswerQuizDAOimpl implements AnswerQuizDAO {
    private Connection connection;
    public AnswerQuizDAOimpl(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public List<AnswerQuiz> getAllAnswerByQuestionQuizID(String questionQuizID) throws SQLException {
        List<AnswerQuiz> answers = new ArrayList<>();
        String sql = "SELECT * FROM answerquiz WHERE IdQuestionQuiz = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, questionQuizID);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String idAnswerQuiz = resultSet.getString("IdAnswerQuiz");
                    String content = resultSet.getString("Content");
                    boolean isCorrect = resultSet.getBoolean("IsCorrect");

                    // Tạo một câu trả lời mới và thêm vào danh sách
                    AnswerQuiz answer = new AnswerQuiz(idAnswerQuiz, content, isCorrect, questionQuizID);
                    answers.add(answer);
                }
            }
        }

        return answers;
    }
    @Override
    public String generateUniqueAnswerId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmssSSS");
        String timestamp = now.format(formatter);
        String idAnswerQuiz = "A" + timestamp.substring(0, 9);
        return idAnswerQuiz;
    }

    @Override
    public boolean addNewAnswer(String content, boolean isCorrect, String idQuestionQuiz) {
        String idAnswerQuiz = generateUniqueAnswerId();

        String sql = "INSERT INTO answerquiz (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idAnswerQuiz);  // Use idAnswerQuiz directly
            statement.setString(2, content);
            statement.setBoolean(3, isCorrect);
            statement.setString(4, idQuestionQuiz);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean editAnswer(String idAnswerQuiz, String content, boolean isCorrect, String idQuestionQuiz) {
        String sql = "UPDATE answerquiz SET Content = ?, IsCorrect = ? WHERE IdAnswerQuiz = ? AND IdQuestionQuiz = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, content);
            statement.setBoolean(2, isCorrect);
            statement.setString(3, idAnswerQuiz);
            statement.setString(4, idQuestionQuiz);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public String convertToChar(String input) {
        char[] charArray = new char[10];
        Arrays.fill(charArray, 'A');
        char[] inputChars = input.toCharArray();
        int length = Math.min(inputChars.length, charArray.length);
        System.arraycopy(inputChars, 0, charArray, 0, length);
        return new String(charArray);
    }
}
