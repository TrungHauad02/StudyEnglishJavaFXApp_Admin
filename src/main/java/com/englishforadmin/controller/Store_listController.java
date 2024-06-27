package com.englishforadmin.controller;

import com.englishforadmin.MainApplication;
import com.englishforadmin.StateManager;
import com.englishforadmin.dao.VocabularyDAO;
import com.englishforadmin.daoimpl.QuizDAOimpl;
import com.englishforadmin.daoimpl.VocabularyDAOimpl;
import com.englishforadmin.myconnection.MySQLconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Lesson;
import model.Quiz;
import model.Vocabulary;

public class Store_listController  implements Initializable {

    public TableColumn columnPhoneticVocabulary;
    @FXML
    private MFXButton btnAddNewVolcabulary;

    @FXML
    private Button btnExit;

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
    private MFXButton btnSearchVolcabulary;

    @FXML
    private MFXButton btnUserInformation;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Pane pnMain;

    @FXML
    private ScrollPane pnMainVolcabulary;

    @FXML
    private TextField txtSearchVolcabulary;

    @FXML
    void ManageLessonScreen(ActionEvent event) throws IOException {
        try {
            MainApplication.loadForm("", "main.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void EditVolcabularyScreen() {
        try {
            MainApplication.loadForm("/volcabularyStore", "store_editVolcabulary.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void ManageStoreVocabularyScreen(ActionEvent event) throws IOException {
        try {
            MainApplication.loadForm("/volcabularyStore", "Store_list.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ManageQuizScreen(ActionEvent event) throws IOException {
        try {
            MainApplication.loadForm("/quiz", "Quiz_list.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AddNewVolcabularyScreen(ActionEvent event) throws IOException {
        try {
            MainApplication.loadForm("/volcabularyStore", "store_newVolcabulary.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // after all:
    @FXML
    void ProfileUserScreen(ActionEvent event) throws IOException {
        try {
            MainApplication.loadForm("/", ".fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //


    @FXML
    private TableColumn<Vocabulary, Vocabulary> columnDeleteButton;

    @FXML
    private TableColumn<Vocabulary, Vocabulary> columnModifyButton;

    @FXML
    private TableColumn<?, ?> columnIDVocabulary;

    @FXML
    private TableColumn<?, ?> columnWordVocabulary;

    @FXML
    private TableColumn<?, ?> columnMeanVocabulary;

    @FXML
    private TableColumn<?, ?> columnImageVocabulary;

    @FXML
    private TableView<Vocabulary> tableViewVocabulary;

    //private VocabularyDAOimpl vocabularyDAOimpl;

    private VocabularyDAOimpl vocabularyDAOimpl;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Connection connection = MySQLconnection.getConnection();
        this.vocabularyDAOimpl = new VocabularyDAOimpl(connection);

        columnIDVocabulary.setCellValueFactory(new PropertyValueFactory<>("IdVocabulary"));
        columnWordVocabulary.setCellValueFactory(new PropertyValueFactory<>("Word"));
        columnMeanVocabulary.setCellValueFactory(new PropertyValueFactory<>("Mean"));
        columnPhoneticVocabulary.setCellValueFactory(new PropertyValueFactory<>("Phonetic"));

        // Cài đặt CellFactory cho cột Modify
        columnModifyButton.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Modify");

            {
                btn.getStyleClass().add("modify-button");
                btn.setOnAction(event -> {
                    Vocabulary vocabulary = getTableView().getItems().get(getIndex());
                    // Hành động khi nút Modify được nhấn
                    try {
                        modifyVocabulary(vocabulary);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Vocabulary vocabulary, boolean empty) {
                super.updateItem(vocabulary, empty);
                setGraphic(empty ? null : btn);
            }
        });

// Cài đặt CellFactory cho cột Delete
        columnDeleteButton.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Delete");

            {
                btn.getStyleClass().add("delete-button");
                btn.setOnAction(event -> {
                    Vocabulary vocabulary = getTableView().getItems().get(getIndex());
                    // Hành động khi nút Delete được nhấn
                    deleteVocabulary(vocabulary);
                });
            }

            @Override
            protected void updateItem(Vocabulary item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        loadStore();
    }


    private void loadStore() {
        try {
            List<Vocabulary> vocabularies = vocabularyDAOimpl.getAllVocabularys();
            ObservableList<Vocabulary> vocabularyObservableList = FXCollections.observableList(vocabularies);
            tableViewVocabulary.setItems(vocabularyObservableList);
        } catch (SQLException e) {
            System.out.println("Không thể tải danh sách bài học: " + e.getMessage());
        }
    }
    private void modifyVocabulary(Vocabulary vocabulary) throws SQLException {

        //System.out.println("Modify vocabulary: " + vocabulary);
        Vocabulary vocabularyEdit = vocabularyDAOimpl.getVocabularyById(vocabulary.getIdVocabulary());
        StateManager.setCurrentVocabulary(vocabulary);
        EditVolcabularyScreen();
    }

    private void deleteVocabulary(Vocabulary vocabulary) {
        // Add your logic to delete the vocabulary item
        // For example:
        System.out.println("Delete vocabulary: " + vocabulary);

    }

}


