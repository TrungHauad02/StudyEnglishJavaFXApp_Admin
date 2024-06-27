package com.englishforadmin.controller;

import com.englishforadmin.MainApplication;
import com.englishforadmin.NavigationManager;
import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.QuestionQuizDAOimpl;
import com.englishforadmin.myconnection.MySQLconnection;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import model.AnswerQuiz;
import model.QuestionQuiz;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Arrays;


public class Quiz_editQuestionController {
    public ImageView imgView;
    public MFXButton btnEditQuestion;
    @FXML
    private MFXButton btnCancelEditQuizQuestion;

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
    private MFXButton btnQuizQuestion01;

    @FXML
    private MFXButton btnQuizQuestion02;

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
    private Pane pnEditLessonQuestion;

    @FXML
    private Pane pnImageSource;

    @FXML
    private Pane pnMain;

    @FXML
    private Pane pnQuizContent;

    @FXML
    private Pane pnTitleEditLessonQuestion;

    @FXML
    private TextArea txtareaContent;

    // fixing
    @FXML
    void Quiz_editAnswerScreen(ActionEvent event) throws IOException {

        NavigationManager navigationManager = NavigationManager.getInstance();
        Scene previousScene = MainApplication.getPreviousScene();
        System.out.println(previousScene.getRoot().getId());
        navigationManager.setPreviousScene(previousScene);

        StateManager.setQuestionID(question.getIdQuestionQuiz());
        try {
            MainApplication.loadForm("/quiz", "Quiz_editAnswer.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CancelQuizAnswer_edit(ActionEvent event) throws IOException {
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
    //---------------------main function --------------------------------------------------------------------------------
    // update Question quiz

    QuestionQuiz question = StateManager.getCurrentQuestion();
    QuestionQuizDAOimpl questionQuizDAOimpl;
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
                showAlert("Error", "Failed to choose image ", String.valueOf(Alert.AlertType.ERROR));
            }
        }

    }
    public void initialize() {
        if (question != null) {
            txtareaContent.setText(question.getContent());
            lblQuizQuestioNumber.setText(String.valueOf(question.getSerial()));
            lblImageSource.setText(Arrays.toString(question.getImage()));

            byte[] imageData = question.getImage();
            if (imageData != null) {
                InputStream is = new ByteArrayInputStream(imageData);
                Image image = new Image(is);
                imgView.setImage(image);
            } else {
                // Nếu image là null - gán một hình ảnh mặc định hoặc không làm gì cả
            }

            questionQuizDAOimpl = new QuestionQuizDAOimpl(MySQLconnection.getConnection());
        } else {
            System.out.println("Dữ liệu gặp lỗi");
        }
    }
    @FXML
    void SubmitQuiz_editQuestion(ActionEvent event) throws IOException {
        editQuestion();
    }

    @FXML
    void editQuestion() {
        String content = txtareaContent.getText();
        int serial = Integer.parseInt(lblQuizQuestioNumber.getText());
        if (question == null) {
            showAlert("Lỗi", "Không thể cập nhật câu hỏi", "Dữ liệu câu hỏi không hợp lệ.");
            return;
        }

        question.setContent(content);
        question.setSerial(serial);
        question.setImage(image);
        try {

            boolean updated = questionQuizDAOimpl.updateQuestionQuiz(question);
            if (updated) {
                showAlert("Thành công", "Cập nhật câu hỏi thành công", "");
            } else {
                showAlert("Lỗi", "Không thể cập nhật câu hỏi", "Đã xảy ra lỗi khi cập nhật câu hỏi.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể cập nhật câu hỏi", "Đã xảy ra lỗi SQL khi cập nhật câu hỏi.");
        }
    }

    // Hàm hiển thị thông báo
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        alert.showAndWait();
    }
}

