package com.englishforadmin.controller;

import com.englishforadmin.DataManager;
import com.englishforadmin.MainApplication;
import com.englishforadmin.NavigationManager;
import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.QuestionQuizDAOimpl;
import com.englishforadmin.myconnection.MySQLconnection;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.QuestionQuiz;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;

public class Quiz_newQuestionController {


    @FXML
    private MFXButton btnSubmitNewQuestion;
    @FXML
    private MFXButton btnCancelNewQuizQuestion;

    @FXML
    private MFXButton btnChooseImgQuiz;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMaximise;

    @FXML
    private Button btnMinimise;

    @FXML
    private MFXButton btnNext;


    @FXML
    private MFXButton btnUserInformation;

    @FXML
    private GridPane gridpnQuizQuestion;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Label lblImageSource;

    @FXML
    private Label lblQuizQuestioNumber;

    @FXML
    private Pane pnImageSource;

    @FXML
    private Pane pnMain;

    @FXML
    private Pane pnNewLesson;

    @FXML
    private Pane pnQuizContent;

    @FXML
    private TextArea txtareaContent;

    // fixing
    @FXML
    void Quiz_newAnswerScreen(ActionEvent event) throws IOException {


        NavigationManager navigationManager = NavigationManager.getInstance();
        Scene previousScene = MainApplication.getPreviousScene();
        System.out.println(previousScene.getRoot().getId());
        navigationManager.setPreviousScene(previousScene);
        try {
            navigationManager.loadForm("/quiz", "Quiz_newAnswer.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CancelQuiz_newQuestion(ActionEvent event) throws IOException {
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
    //-------------------------------------main function -------------------------------------------------


    @FXML
    private ImageView imgView;
    private QuestionQuizDAOimpl questionQuizDAOimpl;
    private byte[] image;

    // choose image
    @FXML
    void chooseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh");
        byte[] imageData = new byte[0];
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Ảnh", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(btnChooseImgQuiz.getScene().getWindow());
        if (selectedFile != null) {
            try {

                imageData = Files.readAllBytes(selectedFile.toPath());
                lblImageSource.setText(selectedFile.getName());
                image = imageData;
                Image imageView = new Image(selectedFile.toURI().toString());
                imgView.setImage(imageView);

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to choose image ", Alert.AlertType.ERROR);
            }
        }

    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void newQuestionButton(ActionEvent event) {
        String content = txtareaContent.getText();
        int questionNumber = Integer.parseInt(lblQuizQuestioNumber.getText());
        byte[] imageData = image;
        String quizID = StateManager.getCurrentQuiz().getIdQuiz();
        String questionID = questionQuizDAOimpl.createQuestion(content, questionNumber, quizID, imageData);
        StateManager.setQuestionID(questionID);
        System.out.println(questionID);

    }


    @FXML
    public void initialize() {
        questionQuizDAOimpl = new QuestionQuizDAOimpl(MySQLconnection.getConnection());
        String quizQuestionNumber = DataManager.getInstance().getQuizQuestionNumber();
        lblQuizQuestioNumber.setText(quizQuestionNumber);


    }
}
