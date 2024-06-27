package com.englishforadmin.controller;

import com.englishforadmin.MainApplication;
import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.VocabularyDAOimpl;
import com.englishforadmin.myconnection.MySQLconnection;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Quiz;
import model.Vocabulary;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class Store_editController {
    @FXML
    public ImageView ImgViewVocabulary;
    public TextField txtword;
    @FXML
    private MFXButton btnCancelEditVolcabulary;

    @FXML
    private MFXButton btnChooseImg;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMaximise;

    @FXML
    private Button btnMinimise;

    @FXML
    private MFXButton btnSubmitEditVolcabulary;

    @FXML
    private MFXButton btnUserInformation;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Label lblImageSource;

    @FXML
    private Pane pnImageSource;

    @FXML
    private Pane pnLessonContent;

    @FXML
    private Pane pnMain;

    @FXML
    private Pane pnNewLesson;

    @FXML
    private Pane pnTitleNewLesson;

    @FXML
    private TextArea txtMoTa;

    @FXML
    private TextField txtPhonetic;

    @FXML
    private TextArea txtareaAntonyms;

    @FXML
    private TextArea txtareaSynonyms;


    // fixing
    @FXML
    void SubmitStoreVolcabulary_edit(ActionEvent event) throws IOException {
        // nhảy lại form trước
        // load lại list StoreVolcabulary ( nội dung vừa edit StoreVolcabulary )
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene previousScene = MainApplication.getPreviousScene();
        if (previousScene != null) {
            currentStage.setScene(previousScene);
        }
    }

    @FXML
    void CancelStoreVolcabulary_edit(ActionEvent event) throws IOException {
        // nhảy lại form trước
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene previousScene = MainApplication.getPreviousScene();
        if (previousScene != null) {
            currentStage.setScene(previousScene);
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
    //-------------------- main function ----------------------------------\

    Quiz quiz = StateManager.getCurrentQuiz();
    Vocabulary currentVocabulary = StateManager.getCurrentVocabulary();

    VocabularyDAOimpl vocabularyDAOimpl;
    private byte[] image;



    @FXML
    public void initialize() throws SQLException {
        Vocabulary currentVocabulary = StateManager.getCurrentVocabulary();
        if (currentVocabulary != null) {
            vocabularyDAOimpl = new VocabularyDAOimpl(MySQLconnection.getConnection());
            String currentVocabularyID = currentVocabulary.getIdVocabulary();
            currentVocabulary = vocabularyDAOimpl.getVocabularyById(currentVocabularyID);
            txtword.setText(currentVocabulary.getWord());
            txtPhonetic.setText(currentVocabulary.getPhonetic());
            txtMoTa.setText(currentVocabulary.getMean());

            byte[] imageData = currentVocabulary.getImage();
            if (imageData != null) {
                InputStream is = new ByteArrayInputStream(imageData);
                Image image = new Image(is);
                ImgViewVocabulary.setImage(image);
            } else {
                // Nếu hình ảnh là null - gán một hình ảnh mặc định hoặc không làm gì cả
            }
        }
    }


}


