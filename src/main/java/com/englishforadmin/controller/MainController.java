package com.englishforadmin.controller;


import com.englishforadmin.MainApplication;
import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.LessonDAOimpl;
import com.englishforadmin.myconnection.MySQLconnection;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Lesson;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class MainController implements Initializable
{
    @FXML
    private MFXButton btnAddNewLesson;
    @FXML
    private MFXButton btnAvatar;

    @FXML
    private Button btnExit;

    @FXML
    private MFXButton btnGrammar;

    @FXML
    private MFXButton btnListening;

    @FXML
    private MFXButton btnLogout;

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
    private MFXButton btnSpeaking;

    @FXML
    private MFXButton btnVolcabulary;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Pane pnMain;

    @FXML
    private TableView<Lesson> tableViewLesson;



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
    void AddNewLessonScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/lesson", "Lesson_newB1.fxml");
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
    void EditLessonScreen()
    {
        try {
            MainApplication.loadForm("/lesson", "Lesson_editB1.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //


    //--------------------------------------main function----------------------------------------------
    //load lesson

    @FXML
    private TableColumn<Lesson, Void> columnModifyButton;
    @FXML
    private TableColumn<Lesson, Void> columnDeleteButton;


    @FXML
    private TableColumn<?, ?> columnIDLesson;

    @FXML
    private TableColumn<?, ?> columnNameLesson;

    @FXML
    private TableColumn<?, ?> columnSerialLesson;

    @FXML
    private TableColumn<?, ?> columnStatus;

    private LessonDAOimpl lessonDAOimpl;
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        Connection connection = MySQLconnection.getConnection();
        this.lessonDAOimpl = new LessonDAOimpl(connection);

        columnIDLesson.setCellValueFactory(new PropertyValueFactory<>("IdLesson"));
        columnIDLesson.setVisible(false);
        columnNameLesson.setCellValueFactory(new PropertyValueFactory<>("Name"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        columnSerialLesson.setCellValueFactory(new PropertyValueFactory<>("Serial"));
        //columnModifyButton delete
        // columnDeleteButton sinh button delete

        // Cài đặt CellFactory cho cột Modify
        columnModifyButton.setCellFactory(col -> new TableCell<Lesson, Void>() {
            private final Button btn = new Button("Modify");
            {
                btn.getStyleClass().add("modify-button");
                btn.setOnAction(event -> {
                    Lesson lesson = getTableView().getItems().get(getIndex());
                    // Hành động khi nút Modify được nhấn
                    modifyLesson(lesson);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        // Cài đặt CellFactory cho cột Delete
        columnDeleteButton.setCellFactory(col -> new TableCell<Lesson, Void>() {
            private final Button btn = new Button("Delete");
            {
                btn.getStyleClass().add("delete-button");
                btn.setOnAction(event -> {
                    Lesson lesson = getTableView().getItems().get(getIndex());
                    // Hành động khi nút Delete được nhấn
                    deleteLesson(lesson);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        // Load dữ liệu cho TableView
        loadLessons();
    }
    private void loadLessons() {
        try {
            List<Lesson> lessons = lessonDAOimpl.getAllLessons();
            ObservableList<Lesson> lessonObservableList = FXCollections.observableArrayList(lessons);

            //System.out.println(lessonObservableList);
            tableViewLesson.setItems(lessonObservableList);
        } catch (SQLException e) {
            System.out.println("Không thể tải danh sách bài học: " + e.getMessage());
        }
    }
    // Phương thức để xử lý sự kiện Modify
    private void modifyLesson(Lesson lesson) {
        Lesson Modifylesson = lessonDAOimpl.getLessonById( lesson.getIdLesson());
        StateManager.setCurrentLesson(Modifylesson);
        EditLessonScreen();

    }


    // Phương thức để xử lý sự kiện Delete
    private void deleteLesson(Lesson lesson) {
        // Logic để xóa bài học





    }











}