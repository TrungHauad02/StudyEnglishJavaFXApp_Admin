package com.englishforadmin.controller;
import com.englishforadmin.MainApplication;
import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.SpeakingDAO;
import com.englishforadmin.feature.MessageBox;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Lesson;
import model.Speaking;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Speaking_editController {
    @FXML
    private MFXButton btnCancelEditSpeaking;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMaximise;

    @FXML
    private Button btnMinimise;

    @FXML
    private MFXButton btnSpeaking01;

    @FXML
    private MFXButton btnSpeaking02;

    @FXML
    private MFXButton btnSubmitEditSpeaking;

    @FXML
    private MFXButton btnUserInformation;

    @FXML
    private GridPane gridpnSpeaking;

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
    private TextArea txtareaContent;

    @FXML
    private TextArea txtareaTitle;

    @FXML
    private MFXButton btnChooseFile;
    @FXML
    private Label lblSrc;

    // fixing
    @FXML
    void SubmitSpeaking_edit(ActionEvent event ) throws IOException
    {
        if(isMissData()){
            MessageBox.show("Lỗi","Hãy điền đầy đủ thông tin trước khi tiếp tục", Alert.AlertType.ERROR);
            return;
        }
        if(isUpdateData()){
            if(!updateSpeaking()){
                MessageBox.show("Lỗi","Cập nhật bài nói thất bại", Alert.AlertType.ERROR);
                return;
            }
            MessageBox.show("Thông báo","Cập nhật bài nói thành công", Alert.AlertType.CONFIRMATION);
            loadData();
            clearField();
        }
    }

    @FXML
    void CancelSpeaking_edit(ActionEvent event ) throws IOException
    {
        // nhảy lại form trước
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene previousScene = MainApplication.getPreviousScene();
        if (previousScene != null) {
            currentStage.setScene(previousScene);
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
    Lesson lesson;
    SpeakingDAO speakingDAO;
    List<Speaking> lstSpeaking;
    Speaking curSpeaking;
    byte[] dataSound;
    @FXML
    public void initialize() {
        lesson = StateManager.getCurrentLesson();
        speakingDAO = new SpeakingDAO();
        loadData();
        btnChooseFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File Sound");
            List<String> soundExtensions = Arrays.asList("*.mp3", "*.wav", "*.ogg", "*.flac");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Video Files", soundExtensions);
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
            if (selectedFile != null) {
                try {
                    dataSound = convertFileToBase64(selectedFile);
                    lblSrc.setText(Arrays.toString(Base64.getDecoder().decode(dataSound)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private byte[] convertFileToBase64(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encode(fileContent);
    }
    private void loadData(){
        lstSpeaking = speakingDAO.selectBySql(SpeakingDAO.SELECT_ALL_SPEAKING_IN_LESSON_QUERY, lesson.getIdLesson());
        loadGridPane();
    }
    private void loadGridPane(){
        gridpnSpeaking.getChildren().clear();
        if(lstSpeaking.isEmpty())
            return;

        int numEntity = lstSpeaking.size();
        int maxColumns = 2;
        int rowCount = (int) Math.ceil((double) numEntity / maxColumns);
        int index = 0;
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < maxColumns; col++) {
                if (index < numEntity) {
                    MFXButton button = new MFXButton(String.valueOf(index));
                    int currentIndex = index;
                    button.setOnAction(e -> {
                        loadDataToFeild(currentIndex);
                    });

                    GridPane.setRowIndex(button, row);
                    GridPane.setColumnIndex(button, col);

                    gridpnSpeaking.getChildren().add(button);
                    index++;
                }
            }
        }
    }
    private void loadDataToFeild(int index){
        curSpeaking = lstSpeaking.get(index);
        txtareaTitle.setText(curSpeaking.getTitle());
        txtareaContent.setText(curSpeaking.getContent());
        dataSound = curSpeaking.getExample();
        if(dataSound!=null){
            lblSrc.setText(Arrays.toString(Base64.getDecoder().decode(dataSound)));
        }
    }

    private boolean isMissData(){
        return txtareaTitle.getText().isEmpty()
                || txtareaContent.getText().isEmpty()
                || lblSrc.getText().isEmpty();
    }

    private void clearField(){
        txtareaTitle.setText("");
        txtareaContent.setText("");
        lblSrc.setText("");
        dataSound = null;
    }
    private boolean isUpdateData(){
        return !curSpeaking.getTitle().equals(txtareaTitle.getText())
                || !curSpeaking.getContent().equals(txtareaContent.getText())
                || !Arrays.equals(curSpeaking.getExample(), dataSound);
    }

    private boolean updateSpeaking(){
        Speaking speaking = new Speaking();
        speaking.setIdSpeaking(curSpeaking.getIdSpeaking());
        speaking.setTitle(txtareaTitle.getText());
        speaking.setContent(txtareaContent.getText());
        speaking.setExample(dataSound);
        return speakingDAO.update(speaking);
    }
}
