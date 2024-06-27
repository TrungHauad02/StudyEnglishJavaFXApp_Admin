package com.englishforadmin.controller;
import com.englishforadmin.MainApplication;
import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.*;
import com.englishforadmin.feature.MessageBox;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Listening_newController {
    @FXML
    private MFXButton btnAvatar;

    @FXML
    private MFXButton btnCancelNewListening;

    @FXML
    private MFXButton btnChooseVideoListening;

    @FXML
    private Button btnExit;

    @FXML
    private MFXButton btnListening01;

    @FXML
    private MFXButton btnListening02;

    @FXML
    private Button btnMaximise;

    @FXML
    private Button btnMinimise;

    @FXML
    private MFXButton btnSubmitNewListening;

    @FXML
    private GridPane gridpnListening;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Label lblSourceVideo;
    @FXML
    private Label lblSrc;

    @FXML
    private Pane pnLessonContent;

    @FXML
    private Pane pnMain;

    @FXML
    private Pane pnNewLesson;

    @FXML
    private Pane pnTitleNewLesson;

    @FXML
    private Pane pnlVideoSource;

    @FXML
    private TextArea txtareaDescription;

    @FXML
    private TextArea txtareaScript;

    @FXML
    private TextArea txtareaTitleListening;

    @FXML
    void SubmitListening_new(ActionEvent event ) throws IOException
    {
        if(isMissData()){
            MessageBox.show("Lỗi","Hãy điền đầy đủ thông tin trước khi tiếp tục", Alert.AlertType.ERROR);
            return;
        }
        if(addNewListening()){
            loadData();
            clearField();
            MessageBox.show("Thành công","Thêm bài nghe thành công", Alert.AlertType.CONFIRMATION);
        }else {
            MessageBox.show("Lỗi","Thêm bài nghe không thành công", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void CancelListening_new(ActionEvent event ) throws IOException
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
    ListeningDAO listeningDAO;
    List<Listening> lstListening;
    Listening curListening;
    byte[] dataVideo;
    @FXML
    public void initialize() {
        lesson = StateManager.getCurrentLesson();
        listeningDAO = new ListeningDAO();
        loadData();
        btnChooseVideoListening.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File Video");
            List<String> videoExtensions = Arrays.asList("*.mp4", "*.avi", "*.mkv", "*.mov", "*.wmv");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Video Files", videoExtensions);
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
            if (selectedFile != null) {
                try {
                    dataVideo = convertVideoToBase64(selectedFile);
                    lblSrc.setText(Arrays.toString(Base64.getDecoder().decode(dataVideo)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void loadData(){
        lstListening = listeningDAO.selectBySql(ListeningDAO.SELECT_LISTENING_FROM_LESSON, lesson.getIdLesson());
        loadGridPane();
    }

    private void loadGridPane(){
        gridpnListening.getChildren().clear();
        if(lstListening.isEmpty())
            return;

        int numEntity = lstListening.size();
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

                    gridpnListening.getChildren().add(button);
                    index++;
                }
            }
        }
    }

    private void loadDataToFeild(int index){
        curListening = lstListening.get(index);

        txtareaTitleListening.setText(curListening.getTitle());
        txtareaScript.setText(curListening.getScript());
        txtareaDescription.setText(curListening.getDescription());
        dataVideo = curListening.getVideo();
        if(dataVideo!=null){
            lblSrc.setText(Arrays.toString(Base64.getDecoder().decode(dataVideo)));
        }
    }

    private byte[] convertVideoToBase64(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encode(fileContent);
    }

    private boolean isMissData(){
        return txtareaTitleListening.getText().isEmpty()
                || txtareaDescription.getText().isEmpty()
                || txtareaScript.getText().isEmpty()
                || lblSrc.getText().isEmpty();
    }
    private boolean addNewListening(){
        Listening listening = new Listening();
        listening.setTitle(txtareaTitleListening.getText());
        listening.setScript(txtareaScript.getText());
        listening.setDescription(txtareaDescription.getText());
        listening.setVideo(dataVideo);
        if (!listeningDAO.insert(listening)){
            return false;
        }

        listening.setIdListening(listeningDAO.getLastestId());
        LessonPart lessonPart = new LessonPart();
        lessonPart.setIdLesson(StateManager.getCurrentLesson().getIdLesson());
        lessonPart.setType(LessonPart.LessonPartType.LISTENING);
        LessonPartDAO lessonPartDAO = new LessonPartDAO();
        if (!lessonPartDAO.insert(lessonPart)){
            return false;
        }

        ListeningPart part = new ListeningPart();
        part.setIdListening(listening.getIdListening());
        part.setIdLessonPart(lessonPartDAO.getLastestId());
        ListeningPartDAO dao = new ListeningPartDAO();
        return dao.insert(part);
    }
    private void clearField(){
        txtareaTitleListening.setText("");
        txtareaDescription.setText("");
        txtareaScript.setText("");
        lblSrc.setText("");
        dataVideo = null;
    }
}
