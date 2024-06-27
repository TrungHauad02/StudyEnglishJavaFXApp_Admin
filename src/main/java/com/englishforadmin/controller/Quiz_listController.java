package com.englishforadmin.controller;

import com.englishforadmin.MainApplication;
import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.QuizDAOimpl;
import com.englishforadmin.myconnection.MySQLconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Lesson;
import model.Quiz;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Quiz_listController implements Initializable {
    @FXML
    private MFXButton btnAddNewQuiz;

    @FXML
    private MFXButton btnAvatar;

    @FXML
    private Button btnExit;

    @FXML
    private MFXButton btnLogOut;

    @FXML
    private MFXButton btnManageLesson;

    @FXML
    private MFXButton btnManageQuiz;

    @FXML
    private MFXButton btnManageVolcabularyStore;

    @FXML
    private Button btnMaximise;

    @FXML
    private Button btnMinimise;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Pane pnDataLesson;

    @FXML
    private Pane pnMain;
    @FXML
    void ManageLessonScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("", "main.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ManageStoreVocabularyScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/volcabularyStore", "Store_list.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ManageQuizScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/quiz", "Quiz_list.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void AddNewQuizScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/quiz", "Quiz_newQuiz.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // after all:
    @FXML
    void ProfileUserScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/", ".fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void EditQuizScreen() throws IOException
    {
        try {
            MainApplication.loadForm("/quiz", "Quiz_editQuiz.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //
    ///--------------------------------main function ----------------------------------------------
    @FXML
    private TableColumn<Quiz, Quiz> columnDeleteButton;

    @FXML
    private TableColumn<?, ?> columnIDQuiz;

    @FXML
    private TableColumn<Quiz, Quiz> columnModifyButton;

    @FXML
    private TableColumn<?, ?> columnStatus;

    @FXML
    private TableColumn<?, ?> columnTitleQuiz;

    @FXML
    private TableView<Quiz> tableViewQuiz;

    private QuizDAOimpl quizDAOimpl;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection connection = MySQLconnection.getConnection();
        this.quizDAOimpl = new QuizDAOimpl(connection);

        columnIDQuiz.setCellValueFactory(new PropertyValueFactory<>("IdQuiz"));
        columnTitleQuiz.setCellValueFactory(new PropertyValueFactory<>("Title"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        // Cài đặt CellFactory cho cột Modify
        columnModifyButton.setCellFactory(col -> new TableCell<Quiz, Quiz>() {
            private final Button btn = new Button("Modify");
            {
                btn.getStyleClass().add("modify-button");
                btn.setOnAction(event -> {
                    Quiz quiz = getTableView().getItems().get(getIndex());
                    // Hành động khi nút Modify được nhấn
                    try {
                        modifyQuiz(quiz);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Quiz item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        // Cài đặt CellFactory cho cột Delete
        columnDeleteButton.setCellFactory(col -> new TableCell<Quiz, Quiz>() {
            private final Button btn = new Button("Delete");
            {
                btn.getStyleClass().add("delete-button");
                btn.setOnAction(event -> {
                    Quiz quiz = getTableView().getItems().get(getIndex());
                    // Hành động khi nút Delete được nhấn
                    deleteQuiz(quiz);
                });
            }

            @Override
            protected void updateItem(Quiz item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        loadQuizs();

    }

    private void deleteQuiz(Quiz quiz) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa quiz này không?");

        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            // Gọi phương thức xóa quiz từ database
            quizDAOimpl.deleteQuiz(quiz.getIdQuiz());
            // Xóa quiz khỏi TableView
            tableViewQuiz.getItems().remove(quiz);
        }
        loadQuizs();
    }

    private void modifyQuiz(Quiz quiz) throws IOException {
        Quiz quizModify = quizDAOimpl.getQuizById( quiz.getIdQuiz());
        StateManager.setCurrentQuiz(quizModify);
        EditQuizScreen();

    }

    private void loadQuizs() {
        try {
            List<Quiz> quizzes = quizDAOimpl.getAllQuizzes();
            ObservableList<Quiz> quizzesObservableList = FXCollections.observableArrayList(quizzes);

            //System.out.println(quizObservableList);
            columnIDQuiz.setSortable(false);
            tableViewQuiz.setItems(quizzesObservableList);
        } catch (SQLException e) {
            System.out.println("Không thể tải danh sách bài học: " + e.getMessage());
        }
    }


}
