package com.englishforadmin.controller;
import com.englishforadmin.MainApplication;
import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.ListeningDAO;
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
import model.Lesson;
import model.Listening;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Listening_editController {
    @FXML
    private MFXButton btnCancelEditListening;

    @FXML
    private MFXButton btnChooseVideo;

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
    private MFXButton btnSubmitEditListening;

    @FXML
    private MFXButton btnUserInformation;

    @FXML
    private GridPane gridpnListening;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Label lblVideoSource;

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
    private Pane pnVideoSource;

    @FXML
    private TextArea txtareaDescription;

    @FXML
    private TextArea txtareaScript;

    @FXML
    private TextArea txtareaTitle;


    @FXML
    void SubmitListening_edit(ActionEvent event ) throws IOException
    {
        if(isMissData()){
            MessageBox.show("Lỗi","Hãy điền đầy đủ thông tin trước khi tiếp tục", Alert.AlertType.ERROR);
            return;
        }
        if(isUpdateData()){
            if(!updateListening()){
                MessageBox.show("Lỗi","Cập nhật bài nghe thất bại", Alert.AlertType.ERROR);
                return;
            }
            MessageBox.show("Thông báo","Cập nhật bài nghe thành công", Alert.AlertType.CONFIRMATION);
            loadData();
            clearField();
        }
    }

    @FXML
    void CancelListening_edit(ActionEvent event ) throws IOException
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
        btnChooseVideo.setOnAction(event -> {
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

        txtareaTitle.setText(curListening.getTitle());
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
        return txtareaTitle.getText().isEmpty()
                || txtareaDescription.getText().isEmpty()
                || txtareaScript.getText().isEmpty()
                || lblSrc.getText().isEmpty();
    }

    private boolean isUpdateData(){
        return !curListening.getDescription().equals(txtareaDescription.getText())
                || !curListening.getTitle().equals(txtareaTitle.getText())
                || !curListening.getScript().equals(txtareaScript.getText())
                || !Arrays.equals(curListening.getVideo(), dataVideo);
    }

    private boolean updateListening(){
        Listening listening = new Listening();
        listening.setIdListening(curListening.getIdListening());
        listening.setDescription(txtareaDescription.getText());
        listening.setTitle(txtareaTitle.getText());
        listening.setScript(txtareaScript.getText());
        listening.setVideo(dataVideo);
        return listeningDAO.update(listening);
    }

    private void clearField(){
        txtareaTitle.setText("");
        txtareaDescription.setText("");
        txtareaScript.setText("");
        lblSrc.setText("");
        dataVideo = null;
    }
}
