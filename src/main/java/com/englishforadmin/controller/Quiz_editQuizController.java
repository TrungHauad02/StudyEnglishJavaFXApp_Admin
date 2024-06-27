package com.englishforadmin.controller;

import com.englishforadmin.DataManager;
import com.englishforadmin.MainApplication;
import com.englishforadmin.NavigationManager;
import com.englishforadmin.StateManager;
import com.englishforadmin.dao.QuestionQuizDAO;
import com.englishforadmin.daoimpl.QuestionQuizDAOimpl;
import com.englishforadmin.daoimpl.QuizDAOimpl;
import com.englishforadmin.myconnection.MySQLconnection;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.skins.MFXToggleButtonSkin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.QuestionQuiz;
import model.Quiz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Quiz_editQuizController {

    @FXML
    private MFXButton btnAvatar;

    @FXML
    private MFXButton btnCancelEditQuiz;

    @FXML
    private MFXButton btnEditQuestionQuiz;

    @FXML
    private MFXButton btnEdit_CreateQuizQuestion;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMaximise;

    @FXML
    private Button btnMinimise;

    @FXML
    private MFXButton btnSubmitEditQuiz;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Pane pnDataLesson;

    @FXML
    private Pane pnMain;

    @FXML
    private RadioButton rdbBlockQuiz;

    @FXML
    private RadioButton rdbOpenQuiz;

    @FXML
    private TextField txtOrderQuiz;

    @FXML
    private TextField txtTitleQuiz;

    @FXML
    private RadioButton rbdHidden;


    @FXML
    void editQuestionScreen(ActionEvent event) throws IOException {
        // if Question!= null -> button private MFXButton btnOpenEditQuiz enable
        try {
            MainApplication.loadForm("/quiz", "Quiz_editQuestion.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AddNewQuestionScreen(ActionEvent event) throws IOException {
        NavigationManager navigationManager = NavigationManager.getInstance();
        navigationManager.setPreviousScene(((Node) event.getSource()).getScene());
        Scene previousScene = navigationManager.getPreviousScene();
        System.out.println(previousScene.getRoot().getId());
        try {
            MainApplication.loadForm("/quiz", "Quiz_newQuestion.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Quiz.QuizStatus getSelectedStatus() {
        if (rdbOpenQuiz.isSelected()) {
            return Quiz.QuizStatus.unlock;
        } else if (rdbBlockQuiz.isSelected()) {
            return Quiz.QuizStatus.lock;
        } else if (rbdHidden.isSelected()) {
            return Quiz.QuizStatus.hidden;
        }
        // Thêm xử lý nếu không có lựa chọn nào được chọn
        return null;
    }


    @FXML
    void CancelQuiz_edit(ActionEvent event) throws IOException {
        try {
            MainApplication.loadForm("/quiz", "Quiz_list.fxml");
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

    //--------------------------------------main function----------------------------------------------


    // fix toggle Group radio button
    @FXML
    private ToggleGroup radioGroupQuiz = new ToggleGroup();

    // add gridpane auto-create Question
    @FXML
    private FlowPane gridPaneQuestionQuiz;
    private QuestionQuizDAOimpl questionQuizDAOimpl;
    Quiz quiz = StateManager.getCurrentQuiz();

    int lastQuestionNumber;
    private QuizDAOimpl quizDAOimpl;

    @FXML
    void SubmitQuiz_edit(ActionEvent event) throws IOException {
        // load quiz list  +  submit add/edit quiz : quiz.getID = > statemanager,getcurrentQuiz

        String title = txtTitleQuiz.getText();
        Quiz.QuizStatus status = getSelectedStatus();

        quiz.setTitle(title);
        quiz.setStatus(status);

        boolean success = quizDAOimpl.updateQuiz(quiz);

        if (success) {


            try {
                MainApplication.loadForm("/quiz", "Quiz_list.fxml");
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void handleButtonActionEditQuestion(QuestionQuiz question) {
        StateManager.setCurrentQuestion(question);
        try {
            MainApplication.loadForm("/quiz", "Quiz_editQuestion.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setToggleGroupQuizStatus() {
        rdbOpenQuiz.setToggleGroup(radioGroupQuiz);
        rdbBlockQuiz.setToggleGroup(radioGroupQuiz);
        rbdHidden.setToggleGroup(radioGroupQuiz);
    }

    public void createGridpaneQuestionByQuizID(List<QuestionQuiz> questionQuizzes) {
        int numberOfQuestions = questionQuizzes.size();
        for (int i = 0; i < numberOfQuestions; i++) {
            // giữ giá trị của question
            final QuestionQuiz question = questionQuizzes.get(i);
            // cập nhật serial trùng với gridpaneQuestionQuiz
            question.setSerial(i + 1);
            Button button = new Button(" " + (i + 1) + " ");
            GridPane.setMargin(button, new Insets(15));
            button.setOnAction(event -> handleButtonActionEditQuestion(question));
            gridPaneQuestionQuiz.getChildren().add(button);
        }

    }
    private void updateQuestionSerial(List<QuestionQuiz> questionList) {
        if (questionList != null) {
            for (int i = 0; i < questionList.size(); i++) {
                QuestionQuiz question = questionList.get(i);
                question.setSerial(i +1 );
                try {
                    questionQuizDAOimpl.updateQuestionQuiz(question);
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to update question serial", Alert.AlertType.ERROR);
                }
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
    public void initialize() {

        if (quiz != null) {
            String quizID = quiz.getIdQuiz();
            setToggleGroupQuizStatus();

            quizDAOimpl = new QuizDAOimpl(MySQLconnection.getConnection());
            questionQuizDAOimpl = new QuestionQuizDAOimpl(MySQLconnection.getConnection());

            List<QuestionQuiz> questionQuizzes;
            try {
                questionQuizzes = questionQuizDAOimpl.getAllQuestionByQuizID(quizID);
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
            int numberOfQuestions = questionQuizzes.size();
            double prefWrapLength = calculatePrefWrapLength();

            gridPaneQuestionQuiz.setHgap(10);
            gridPaneQuestionQuiz.setVgap(10);
            gridPaneQuestionQuiz.setPadding(new Insets(10));
            gridPaneQuestionQuiz.setPrefWrapLength(prefWrapLength);

            // set Question number for new question interface
            lastQuestionNumber = numberOfQuestions + 1;
            DataManager.getInstance().setQuizQuestionNumber(String.valueOf(lastQuestionNumber));

            createGridpaneQuestionByQuizID(questionQuizzes);

            updateQuestionSerial(questionQuizzes);

            txtOrderQuiz.setText(quiz.getIdQuiz());
            txtOrderQuiz.setEditable(false);
            txtTitleQuiz.setText(quiz.getTitle());

            if (quiz.getStatus() == Quiz.QuizStatus.unlock) {
                rdbOpenQuiz.setSelected(true);
            } else if (quiz.getStatus() == Quiz.QuizStatus.lock) {
                rdbBlockQuiz.setSelected(true);
            } else {
                rbdHidden.setSelected(true);
            }
        }
    }

    private double calculatePrefWrapLength() {
        int numberOfColumns = 2;
        double columnWidth = 100;
        double hGap = 10;
        double prefWrapLength = numberOfColumns * (columnWidth + hGap);
        return prefWrapLength;
    }


}

