package com.englishforadmin.daoimpl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.englishforadmin.myconnection.MySQLconnection;
import model.Grammar;

public class GrammarDAO{
    public static final String SELECT_ALL_GRAMMAR_IN_LESSON_QUERY =
            "SELECT g.IdGrammar, g.Title, g.Content, g.Rule, g.Image, g.Example\n" +
                    "FROM GRAMMAR g\n" +
                    "JOIN GRAMMARPART gp ON g.IdGrammar = gp.IdGrammar\n" +
                    "JOIN LESSONPART lp ON gp.IdLessonPart = lp.IdLessonPart\n" +
                    "JOIN LESSON l ON lp.IdLesson = l.IdLesson\n" +
                    "WHERE l.IdLesson = ?;\n";
    private static final String INSERT_GRAMMAR_QUERY =
            "INSERT INTO GRAMMAR (Title, Content, Rule, Image, Example) " +
                    "VALUES (?,?,?,?,?);";
    private static final String UPDATE_GRAMMAR_QUERY =
            "UPDATE GRAMMAR \n" +
            "SET Title = ?, Content = ?, Rule = ?, Image = ?, Example = ?\n" +
            "WHERE IdGrammar = ?;";
    private final static String SELECT_LATEST_ID_GRAMMAR_QUERY =
            "SELECT IdGrammar\n" +
                    "FROM GRAMMAR\n" +
                    "ORDER BY IdGrammar DESC\n" +
                    "LIMIT 1;";
    public boolean insert(Grammar entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GRAMMAR_QUERY)) {
                preparedStatement.setString(1, entity.getTitle());
                preparedStatement.setString(2, entity.getContent());
                preparedStatement.setString(3, entity.getRule());
                preparedStatement.setBytes(4, entity.getImage());
                preparedStatement.setString(5, entity.getExample());

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
    public boolean update(Grammar entity){
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GRAMMAR_QUERY)) {
                preparedStatement.setString(1, entity.getTitle());
                preparedStatement.setString(2, entity.getContent());
                preparedStatement.setString(3, entity.getRule());
                preparedStatement.setBytes(4, entity.getImage());
                preparedStatement.setString(5, entity.getExample());
                preparedStatement.setString(6, entity.getIdGrammar());

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
    public Grammar selectById(String id){
        return null;
    }
    public List<Grammar> selectAll(){
        return null;
    }
    public List<Grammar> selectBySql(String sql, Object...args){
        List<Grammar> lstGrammar = new LinkedList<>();
        Connection connection = MySQLconnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Grammar grammar = new Grammar();
                    grammar.setIdGrammar(resultSet.getString("IdGrammar"));
                    grammar.setTitle(resultSet.getString("Title"));
                    grammar.setContent(resultSet.getString("Content"));
                    grammar.setRule(resultSet.getString("Rule"));
                    grammar.setImage(resultSet.getBytes("Image"));
                    grammar.setExample(resultSet.getString("Example"));
                    lstGrammar.add(grammar);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lstGrammar;
    }

    public String getLastestId(){
        Connection connection = MySQLconnection.getConnection();
        String id = "";
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LATEST_ID_GRAMMAR_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    id = resultSet.getString("IdGrammar");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (id.isEmpty())
            id = "gram000001";
        return id;
    }
}
