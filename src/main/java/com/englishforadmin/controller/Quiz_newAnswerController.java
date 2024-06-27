package com.englishforadmin.controller;

import com.englishforadmin.MainApplication;
import com.englishforadmin.NavigationManager;
import com.englishforadmin.StateManager;
import com.englishforadmin.dao.AnswerQuizDAO;
import com.englishforadmin.daoimpl.AnswerQuizDAOimpl;
import com.englishforadmin.myconnection.MySQLconnection;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.AnswerQuiz;
import model.QuestionQuiz;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Quiz_newAnswerController  implements Initializable {
    public AnchorPane rootPane;
    @FXML
    private ToggleGroup IsCorrectGroupAnswer1;

    @FXML
    private ToggleGroup IsCorrectGroupAnswer2;

    @FXML
    private ToggleGroup IsCorrectGroupAnswer3;

    @FXML
    private ToggleGroup IsCorrectGroupAnswer4;
    @FXML
    private MFXButton btnCancelNewQuiz;

    @FXML
    private MFXButton btnChooseImg01;

    @FXML
    private MFXButton btnChooseImg02;

    @FXML
    private MFXButton btnChooseImg03;

    @FXML
    private MFXButton btnChooseImg04;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMaximise;

    @FXML
    private Button btnMinimise;

    @FXML
    private MFXButton btnQuizQuestion01;

    @FXML
    private MFXButton btnQuizQuestion02;

    @FXML
    private MFXButton btnSubmitNewQuiz;

    @FXML
    private MFXButton btnUserInformation;

    @FXML
    private GridPane gridpnQuizQuestion;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Label lblImageSource03;

    @FXML
    private Label lblImageSource04;

    @FXML
    private Label lblQuestionNumber;

    @FXML
    private Label lblSourceImg01;

    @FXML
    private Label lblSourceImg02;

    @FXML
    private Pane pnImageSource;

    @FXML
    private Pane pnImageSource1;

    @FXML
    private Pane pnImageSource11;

    @FXML
    private Pane pnImageSource111;

    @FXML
    private Pane pnLessonContent;

    @FXML
    private Pane pnMain;

    @FXML
    private Pane pnNewLesson;

    @FXML
    private Pane pnTitleNewLesson;

    @FXML
    private RadioButton rdb01_No;

    @FXML
    private RadioButton rdb01_No1;

    @FXML
    private RadioButton rdb01_Yes;

    @FXML
    private RadioButton rdb01_Yes1;

    @FXML
    private RadioButton rdb02_No;

    @FXML
    private RadioButton rdb02_Yes;

    @FXML
    private RadioButton rdb03_No;

    @FXML
    private RadioButton rdb03_Yes;

    @FXML
    private RadioButton rdb04_No;

    @FXML
    private RadioButton rdb04_Yes;

    @FXML
    private AnchorPane scrollpanelMain;

    @FXML
    private TextArea txtareaAnswer01;

    @FXML
    private TextArea txtareaAnswer02;

    @FXML
    private TextArea txtareaAnswer03;

    @FXML
    private TextArea txtareaAnswer04;


    // fixing

    private String previousPage;

    // Setter cho previousPage
    public void setPreviousPage(String page) {
        this.previousPage = page;
    }


    @FXML
    void CancelQuizAnswer(ActionEvent event) throws IOException {
        Scene previousScene = MainApplication.getPreviousScene();
        if (previousScene != null) {
            String previousFXML = previousScene.getRoot().getId();
            if (previousFXML != null) {
                if (previousFXML.equals("Quiz_newQuestion")) {
                    try {
                        MainApplication.loadForm("/quiz", "Quiz_newQuiz.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        MainApplication.loadForm("/quiz", "Quiz_editQuiz.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
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
    //____________________________ main fucntion ------------------------------------

    private AnswerQuizDAOimpl answerQuizDAOimpl;
    @FXML
    void SubmitQuizAnswer_new(ActionEvent event) throws IOException {
            String questionQuizID =  StateManager.getQuestionID();
            addNewAnswerByQuestionID(txtareaAnswer01.getText(), rdb01_Yes.isSelected(), questionQuizID);
            addNewAnswerByQuestionID(txtareaAnswer02.getText(), rdb02_Yes.isSelected(), questionQuizID);
            addNewAnswerByQuestionID(txtareaAnswer03.getText(), rdb03_Yes.isSelected(), questionQuizID);
            addNewAnswerByQuestionID(txtareaAnswer04.getText(), rdb04_Yes.isSelected(), questionQuizID);


        NavigationManager navigationManager = NavigationManager.getInstance();
        Scene previousScene = navigationManager.getPreviousScene(); // Lấy scene trước đó
        if (previousScene != null && previousScene.getRoot().getId() != null) {
            if (previousScene.getRoot().getId().equals("Quiz_editQuiz")) {
                navigationManager.loadForm("/quiz", "Quiz_editQuiz.fxml");
            } else if (previousScene.getRoot().getId().equals("Quiz_newQuiz")) {
                navigationManager.loadForm("/quiz", "Quiz_newQuiz.fxml");
            } else {
                System.out.println("Cannot determine previous page.");
                System.out.println(previousScene.getRoot().getId());
            }
        } else {
            System.out.println("Root ID is null.");
        }
    }

    @FXML
    private void addNewAnswerByQuestionID(String content, boolean isCorrect, String questionQuizID) {
        boolean success = answerQuizDAOimpl.addNewAnswer(content, isCorrect, questionQuizID);
        if (success) {
            System.out.println("Answer added successfully: ");
        } else {
            System.out.println("Failed to add answer: ");
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        answerQuizDAOimpl = new AnswerQuizDAOimpl(MySQLconnection.getConnection());
        rdb01_Yes.setToggleGroup(IsCorrectGroupAnswer1);
        rdb01_No.setToggleGroup(IsCorrectGroupAnswer1);

        rdb02_Yes.setToggleGroup(IsCorrectGroupAnswer2);
        rdb02_No.setToggleGroup(IsCorrectGroupAnswer2);

        rdb03_Yes.setToggleGroup(IsCorrectGroupAnswer3);
        rdb03_No.setToggleGroup(IsCorrectGroupAnswer3);

        rdb04_Yes.setToggleGroup(IsCorrectGroupAnswer4);
        rdb04_No.setToggleGroup(IsCorrectGroupAnswer4);

    }

}
