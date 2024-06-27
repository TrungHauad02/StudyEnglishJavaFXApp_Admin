package com.englishforadmin.controller;
import com.englishforadmin.MainApplication;
import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.LessonDAOimpl;
import com.englishforadmin.feature.MessageBox;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Lesson;

import java.io.IOException;


public class Lesson_newB1Controller {
    @FXML
    private MFXButton btnAvatar;

    @FXML
    private MFXButton btnCancelNewLesson;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMaximise;

    @FXML
    private Button btnMinimise;

    @FXML
    private MFXButton btnNext;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Pane pnLessonContent;

    @FXML
    private Pane pnMain;

    @FXML
    private Pane pnNewLesson;

    @FXML
    private Pane pnTitleNewLesson;

    @FXML
    private RadioButton rdbLockLesson;

    @FXML
    private RadioButton rdbOpenLesson;

    @FXML
    private TextField txtNameLesson;
    @FXML
    private TextField txtSerial;

    @FXML
    private TextArea txtareaDescription;
    LessonDAOimpl lessonDAO;
    @FXML
    void CancelLesson_newB1(ActionEvent event ) throws IOException
    {
        // nhảy lại form trước
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene previousScene = MainApplication.getPreviousScene();
        if (previousScene != null) {
            currentStage.setScene(previousScene);
        }
    }
    @FXML
    void Lesson_newB2Screen(ActionEvent event ) throws IOException
    {
        if(isMissData()){
            MessageBox.show("Lỗi","Hãy điền đầy đủ thông tin trước khi tiếp tục", Alert.AlertType.ERROR);
            return;
        }else if(isDuplicateSerial(Integer.parseInt(txtSerial.getText().trim()))){
            MessageBox.show("Lỗi","Số thứ tự bài học này đã tồn tại", Alert.AlertType.ERROR);
            return;
        }
        if(addNewLesson()){
            try {
                MainApplication.loadForm("/lesson", "Lesson_newB2.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            MessageBox.show("Lỗi","Không thêm được bài học", Alert.AlertType.ERROR);
            return;
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
    @FXML
    public void initialize() {
        lessonDAO = new LessonDAOimpl();
        int serial = lessonDAO.getLastestSerial() + 1;
        txtSerial.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtSerial.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        txtSerial.setText(String.valueOf(serial));
        loadToggleGroup();
    }

    private void loadToggleGroup(){
        ToggleGroup toggleGroup = new ToggleGroup();
        rdbLockLesson.setToggleGroup(toggleGroup);
        rdbOpenLesson.setToggleGroup(toggleGroup);
        rdbOpenLesson.setSelected(true);
    }

    private boolean isMissData(){
        return txtNameLesson.getText().isEmpty() || txtareaDescription.getText().isEmpty() || txtSerial.getText().isEmpty();
    }

    private boolean isDuplicateSerial(int serial){
        return lessonDAO.checkDuplicateSerial(serial);
    }

    private boolean addNewLesson(){
        String name = txtNameLesson.getText();
        String description = txtareaDescription.getText();
        Lesson lesson = new Lesson();
        lesson.setName(name);
        lesson.setDescription(description);
        lesson.setStatus(rdbOpenLesson.isSelected() ? Lesson.LessonStatus.unlock : Lesson.LessonStatus.lock);
        lesson.setSerial(Integer.parseInt(txtSerial.getText().trim()));
        boolean result = lessonDAO.insert(lesson);
        lesson.setIdLesson(lessonDAO.getLastestId());
        StateManager.setCurrentLesson(lesson);
        return result;
    }
}
