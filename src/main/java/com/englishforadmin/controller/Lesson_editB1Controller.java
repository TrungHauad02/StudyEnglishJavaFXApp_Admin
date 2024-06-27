package com.englishforadmin.controller;

import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.LessonDAOimpl;
import com.englishforadmin.feature.MessageBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.englishforadmin.MainApplication;
import io.github.palexdev.materialfx.controls.MFXButton;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Lesson;

import java.io.IOException;

public class Lesson_editB1Controller {
    @FXML
    private MFXButton btnCancel;

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
    private TextArea txtareaDescription;
    @FXML
    private TextField txtSerial;
    LessonDAOimpl lessonDAO;

    @FXML
    void CancelLesson_editB1(ActionEvent event ) throws IOException
    {
        // nhảy lại form trước
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene previousScene = MainApplication.getPreviousScene();
        if (previousScene != null) {
            currentStage.setScene(previousScene);
        }
    }
    @FXML
    void Lesson_editB2Screen(ActionEvent event ) throws IOException
    {
        if(isMissData()){
            MessageBox.show("Lỗi","Hãy điền đầy đủ thông tin trước khi tiếp tục", Alert.AlertType.ERROR);
            return;
        }
        if(isUpdateData()){
            if(StateManager.getCurrentLesson().getSerial() != Integer.parseInt(txtSerial.getText().trim())
                    && isDuplicateSerial(Integer.parseInt(txtSerial.getText().trim()))){
                MessageBox.show("Lỗi","Số thứ tự bài học này đã tồn tại", Alert.AlertType.ERROR);
                return;
            }
            Lesson lesson = new Lesson();
            lesson.setIdLesson(StateManager.getCurrentLesson().getIdLesson());
            if(rdbOpenLesson.isSelected())
                lesson.setStatus(Lesson.LessonStatus.unlock);
            else
                lesson.setStatus(Lesson.LessonStatus.lock);
            lesson.setSerial(Integer.parseInt(txtSerial.getText()));
            lesson.setName(txtNameLesson.getText());
            lesson.setDescription(txtareaDescription.getText());
            if(!lessonDAO.update(lesson)){
                MessageBox.show("Lỗi","Cập nhật bài học thất bại", Alert.AlertType.ERROR);
                return;
            }
            StateManager.setCurrentLesson(lesson);
            MessageBox.show("Thông báo","Cập nhật bài học thành công", Alert.AlertType.CONFIRMATION);
        }
        try {
            MainApplication.loadForm("/lesson", "Lesson_editB2.fxml");
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

    @FXML
    public void initialize() {
        lessonDAO = new LessonDAOimpl();
        Lesson lesson = StateManager.getCurrentLesson();
        loadToggleGroup();
        txtSerial.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtSerial.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        if (lesson != null) {
            txtNameLesson.setText(lesson.getName());
            txtareaDescription.setText(lesson.getDescription());
            if (lesson.getStatus() == Lesson.LessonStatus.unlock) {
                rdbOpenLesson.setSelected(true);
            } else {
                rdbLockLesson.setSelected(true);
            }
            txtSerial.setText(String.valueOf(lesson.getSerial()));
        }
    }
    private void loadToggleGroup(){
        ToggleGroup toggleGroup = new ToggleGroup();
        rdbLockLesson.setToggleGroup(toggleGroup);
        rdbOpenLesson.setToggleGroup(toggleGroup);
    }
    private boolean isMissData(){
        return txtNameLesson.getText().isEmpty() || txtareaDescription.getText().isEmpty() || txtSerial.getText().isEmpty();
    }

    private boolean isDuplicateSerial(int serial){
        return lessonDAO.checkDuplicateSerial(serial);
    }

    private boolean isUpdateData(){
        Lesson curLesson = StateManager.getCurrentLesson();
        boolean isChangeStatus = true;
        if(curLesson.getStatus() == Lesson.LessonStatus.unlock && rdbOpenLesson.isSelected())
            isChangeStatus = false;
        else if(curLesson.getStatus() == Lesson.LessonStatus.lock && rdbLockLesson.isSelected())
            isChangeStatus = false;
        return !curLesson.getName().equals(txtNameLesson.getText())
                || !curLesson.getDescription().equals(txtareaDescription.getText())
                || curLesson.getSerial() != Integer.parseInt(txtSerial.getText().trim())
                || isChangeStatus;
    }
}
