package com.englishforadmin.controller;
import com.englishforadmin.MainApplication;
import com.englishforadmin.NavigationManager;
import com.englishforadmin.daoimpl.QuizDAOimpl;
import com.englishforadmin.myconnection.MySQLconnection;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Quiz;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class Quiz_newQuizController {
    @FXML
    private MFXButton btnCancelNewQuiz;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMaximise;

    @FXML
    private Button btnMinimise;

    @FXML
    private MFXButton btnNewQuiz_final;

    @FXML
    private MFXButton btnOpenEditQuiz;

    @FXML
    private MFXButton btnOpen_AddQuestionQuiz;

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
    private Pane pnDataLesson;

    @FXML
    private Pane pnMain;

    @FXML
    private RadioButton rdbHiddenQuiz;

    @FXML
    private RadioButton rdbLockQuiz;

    @FXML
    private RadioButton rdbOpenQuiz;

    @FXML
    private TextField txtOrder;

    @FXML
    private TextField txtTitleQuiz;

    @FXML
    void editQuestionScreen(ActionEvent event ) throws IOException
    {
        // if Question!= null -> button private MFXButton btnOpenEditQuiz enable
        try {
            MainApplication.loadForm("/quiz", "Quiz_editQuestion.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AddNewQuestionScreen(ActionEvent event ) throws IOException
    {
        NavigationManager navigationManager = NavigationManager.getInstance();
        Node sourceNode = (Node) event.getSource();
        Scene currentScene = sourceNode.getScene();
        navigationManager.setPreviousScene(currentScene);

        System.out.println(currentScene.getRoot().getId());
        try {
            MainApplication.loadForm("/quiz", "Quiz_newQuestion.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void CancelQuiz_new(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/quiz", "Quiz_list.fxml");
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


    //
    //_____________________________main function ________________________________________

    // Hàm này để lấy trạng thái của RadioButton được chọn
    private String getStatusFromRadioButton() {
        if (rdbHiddenQuiz.isSelected()) {
            return "hidden";
        } else if (rdbLockQuiz.isSelected()) {
            return "lock";
        } else if (rdbOpenQuiz.isSelected()) {
            return "unlock";
        } else {
            return "";
        }
    }
    @FXML
    void SubmitQuiz_new(ActionEvent event ) throws IOException {
        // Lấy dữ liệu từ các trường nhập liệu trên giao diện
        String title = txtTitleQuiz.getText();
        String status = getStatusFromRadioButton();

        // Kiểm tra xem các trường nhập liệu có trống không
        if (title.isEmpty() || status.isEmpty()) {
            // Hiển thị thông báo lỗi nếu có trường nào đó không được nhập
            // Ví dụ: Thông báo lỗi bằng cách hiển thị một hộp thoại hoặc gắn một label vào giao diện
            System.out.println("Please fill in all fields!");
            return;
        }

        // Gọi hàm DAO để thêm bài kiểm tra mới
        QuizDAOimpl quizDAO = new QuizDAOimpl(MySQLconnection.getConnection());
        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(title);
        newQuiz.setStatus(Quiz.QuizStatus.valueOf(status));

        boolean success = quizDAO.addNewQuiz(newQuiz);

        // Kiểm tra xem việc thêm mới bài kiểm tra có thành công hay không
        if (success) {
            // Hiển thị hộp thoại thông báo thành công
            showAlert("Success", "Quiz added successfully!", Alert.AlertType.INFORMATION);

            // Nếu thành công, chuyển hướng về giao diện danh sách bài kiểm tra
            try {
                MainApplication.loadForm("/quiz", "Quiz_list.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to add new quiz! ");
        }
    }

    // Hàm để hiển thị hộp thoại thông báo
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {


    }
}
