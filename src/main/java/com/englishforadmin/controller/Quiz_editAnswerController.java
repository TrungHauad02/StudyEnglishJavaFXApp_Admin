package com.englishforadmin.controller;

import com.englishforadmin.MainApplication;
import com.englishforadmin.NavigationManager;
import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.AnswerQuizDAOimpl;
import com.englishforadmin.myconnection.MySQLconnection;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

import java.io.IOException;
import java.util.List;

public class Quiz_editAnswerController {

    @FXML
    private ToggleGroup IsCorrectGroupAnswer1;

    @FXML
    private ToggleGroup IsCorrectGroupAnswer2;

    @FXML
    private ToggleGroup IsCorrectGroupAnswer3;

    @FXML
    private ToggleGroup IsCorrectGroupAnswer4;

    @FXML
    private MFXButton btnCancelEditQuizAnswer;

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
    private MFXButton btnSubmitEditQuizAnswer;

    @FXML
    private MFXButton btnUserInformation;

    @FXML
    private GridPane gridpnQuizQuestion;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Label lblQuestionNumber;

    @FXML
    private Pane pnEditLesson;

    @FXML
    private Pane pnLessonContent;

    @FXML
    private Pane pnMain;

    @FXML
    private Pane pnTitleEditLessonAnswer;

    @FXML
    private RadioButton rdb01_No;

    @FXML
    private RadioButton rdb01_Yes;
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
    @FXML
    void SubmitQuizAnswer_edit(ActionEvent event) throws IOException {

        String questionQuizID =  StateManager.getQuestionID();
        editAnswerByQuestionID(questionQuizID);

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
    void CancelQuizAnswer_edit(ActionEvent event) throws IOException {
        //  // hủy hẳn luôn : -> Quiz_newQuiz
        try {
            MainApplication.loadForm("/quiz", "Quiz_editQuiz.fxml");
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

    // ----------------------------main function -----------------------------------------------------------------

    private AnswerQuizDAOimpl answerQuizDAOimpl;
    private String idAnswer01 ;
    private String idAnswer02 ;
    private String idAnswer03 ;
    private String idAnswer04 ;
    private void displayQuestionAndAnswers() {
        QuestionQuiz question = StateManager.getCurrentQuestion();
        if (question != null) {
            List<AnswerQuiz> answerList = question.getLstAnswers();
            if (answerList != null && !answerList.isEmpty()) {
                lblQuestionNumber.setText(String.valueOf(question.getSerial()));
                txtareaAnswer01.setText(answerList.get(0).getContent());
                idAnswer01 = answerList.get(0).getIdAnswerQuiz();
                txtareaAnswer02.setText(answerList.get(1).getContent());
                idAnswer02 = answerList.get(1).getIdAnswerQuiz();
                txtareaAnswer03.setText(answerList.get(2).getContent());
                idAnswer03 = answerList.get(2).getIdAnswerQuiz();
                txtareaAnswer04.setText(answerList.get(3).getContent());
                idAnswer04 = answerList.get(3).getIdAnswerQuiz();

                // Xử lý giá trị của RadioButton dựa trên đối tượng AnswerQuiz
                handleRadioButtonValues(answerList);
            }
        }
    }

    @FXML
    private void editAnswerByQuestionID(String questionQuizID) {
        String contentAnswer01 = txtareaAnswer01.getText();
        boolean isCorrect01 = rdb01_Yes.isSelected();

        String contentAnswer02 = txtareaAnswer02.getText();
        boolean isCorrect02 = rdb02_Yes.isSelected();

        String contentAnswer03 = txtareaAnswer03.getText();
        boolean isCorrect03 = rdb03_Yes.isSelected();

        String contentAnswer04 = txtareaAnswer04.getText();
        boolean isCorrect04 = rdb04_Yes.isSelected();

        // Thực hiện chỉnh sửa các câu trả lời sử dụng hàm editAnswer
        editAnswer(idAnswer01, contentAnswer01, isCorrect01, questionQuizID);
        editAnswer(idAnswer02, contentAnswer02, isCorrect02, questionQuizID);
        editAnswer(idAnswer03, contentAnswer03, isCorrect03, questionQuizID);
        editAnswer(idAnswer04, contentAnswer04, isCorrect04, questionQuizID);
    }

    private void editAnswer(String idAnswerQuiz, String content, boolean isCorrect, String idQuestionQuiz) {
        boolean success = answerQuizDAOimpl.editAnswer(idAnswerQuiz, content, isCorrect, idQuestionQuiz);
        if (success) {
            System.out.println("Answer edited successfully: " + idAnswerQuiz);
        } else {
            System.out.println("Failed to edit answer: " + idAnswerQuiz);
        }
    }


    @FXML
    public void initialize() {
        answerQuizDAOimpl = new AnswerQuizDAOimpl(MySQLconnection.getConnection());

        rdb01_Yes.setToggleGroup(IsCorrectGroupAnswer1);
        rdb01_No.setToggleGroup(IsCorrectGroupAnswer1);

        rdb02_Yes.setToggleGroup(IsCorrectGroupAnswer2);
        rdb02_No.setToggleGroup(IsCorrectGroupAnswer2);

        rdb03_Yes.setToggleGroup(IsCorrectGroupAnswer3);
        rdb03_No.setToggleGroup(IsCorrectGroupAnswer3);

        rdb04_Yes.setToggleGroup(IsCorrectGroupAnswer4);
        rdb04_No.setToggleGroup(IsCorrectGroupAnswer4);
        displayQuestionAndAnswers();
    }




    private void handleRadioButtonValues(List<AnswerQuiz> answerList) {
        setSelectedRadioButton(IsCorrectGroupAnswer1, answerList.get(0).isCorrect());
        setSelectedRadioButton(IsCorrectGroupAnswer2, answerList.get(1).isCorrect());
        setSelectedRadioButton(IsCorrectGroupAnswer3, answerList.get(2).isCorrect());
        setSelectedRadioButton(IsCorrectGroupAnswer4, answerList.get(3).isCorrect());
    }

    private void setSelectedRadioButton(ToggleGroup toggleGroup, boolean isCorrect) {
        RadioButton selectedRadioButton = null;
        if (isCorrect) {
            selectedRadioButton = (RadioButton) toggleGroup.getToggles().get(0);
        } else {
            selectedRadioButton = (RadioButton) toggleGroup.getToggles().get(1);
        }
        selectedRadioButton.setSelected(true);
    }

}
