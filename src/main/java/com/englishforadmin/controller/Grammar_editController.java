package com.englishforadmin.controller;
import com.englishforadmin.MainApplication;
import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.GrammarDAO;
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
import model.Grammar;
import model.Lesson;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Grammar_editController {
    @FXML
    private MFXButton btnCancelEditGrammar;

    @FXML
    private MFXButton btnChooseImage_grammar;

    @FXML
    private Button btnExit;

    @FXML
    private MFXButton btnGrammar01;

    @FXML
    private MFXButton btnGrammar02;

    @FXML
    private Button btnMaximise;

    @FXML
    private Button btnMinimise;

    @FXML
    private MFXButton btnSubmitEditGrammar;

    @FXML
    private MFXButton btnUserInformation;

    @FXML
    private GridPane gridpnGrammar_edit;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Label lblImageSource;

    @FXML
    private Pane pnImageSource;

    @FXML
    private Pane pnLessonContent;

    @FXML
    private Pane pnMain;

    @FXML
    private Pane pnNewLesson;

    @FXML
    private TextArea txtareaContent;

    @FXML
    private TextArea txtareaExampleGrammar;

    @FXML
    private TextArea txtareaRuleGrammar;

    @FXML
    private TextArea txtareaTitle;
    @FXML
    private GridPane gridpnGrammar;
    @FXML
    private Label lblSrc;
    // fixing
    @FXML
    void SubmitGrammar_edit(ActionEvent event ) throws IOException
    {

        if(isMissData()){
            MessageBox.show("Lỗi","Hãy điền đầy đủ thông tin trước khi tiếp tục", Alert.AlertType.ERROR);
            return;
        }
        if(isUpdateData()){
            if (!updateGrammar()){
                MessageBox.show("Lỗi","Cập nhật ngữ pháp thất bại", Alert.AlertType.ERROR);
                return;
            }
            MessageBox.show("Thông báo","Cập nhật ngữ pháp thành công", Alert.AlertType.CONFIRMATION);
            loadData();
            clearField();
        }
    }

    @FXML
    void CancelGrammar_edit(ActionEvent event ) throws IOException
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
    List<Grammar> lstGrammar;
    GrammarDAO grammarDAO;
    Grammar curGrammar;
    Lesson lesson;
    byte[] dataImage;
    @FXML
    public void initialize() {
        lesson = StateManager.getCurrentLesson();
        grammarDAO = new GrammarDAO();
        loadData();
        btnChooseImage_grammar.setOnAction(event1 -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image File");
            List<String> imageExtensions = Arrays.asList("*.jpg", "*.jpeg", "*.png", "*.gif");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", imageExtensions);
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog((Stage) ((Node) event1.getSource()).getScene().getWindow());

            if (selectedFile != null) {
                try {
                    dataImage = convertImageToBase64(selectedFile);
                    lblSrc.setText(Arrays.toString(Base64.getDecoder().decode(dataImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private byte[] convertImageToBase64(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encode(fileContent);
    }
    private void loadData(){
        lstGrammar = grammarDAO.selectBySql(GrammarDAO.SELECT_ALL_GRAMMAR_IN_LESSON_QUERY, lesson.getIdLesson());
        loadGridPane();
    }

    private void loadGridPane(){
        gridpnGrammar.getChildren().clear();
        if(lstGrammar.isEmpty())
            return;
        int numEntity = lstGrammar.size();
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

                    gridpnGrammar.getChildren().add(button);
                    index++;
                }
            }
        }
    }

    private void loadDataToFeild(int index){
        curGrammar = lstGrammar.get(index);

        txtareaTitle.setText(curGrammar.getTitle());
        txtareaContent.setText(curGrammar.getContent());
        txtareaRuleGrammar.setText(curGrammar.getRule());
        txtareaExampleGrammar.setText(curGrammar.getExample());
        dataImage = curGrammar.getImage();
        if(dataImage!=null){
            lblSrc.setText(Arrays.toString(Base64.getDecoder().decode(dataImage)));
        }
    }

    private boolean isMissData(){
        return txtareaTitle.getText().isEmpty() || txtareaContent.getText().isEmpty()
                || txtareaRuleGrammar.getText().isEmpty() || txtareaExampleGrammar.getText().isEmpty();
    }

    private boolean isUpdateData(){
        return !curGrammar.getContent().equals(txtareaContent.getText())
                || !curGrammar.getTitle().equals(txtareaTitle.getText())
                || !curGrammar.getRule().equals(txtareaRuleGrammar.getText())
                || !curGrammar.getExample().equals(txtareaExampleGrammar.getText())
                || !Arrays.equals(curGrammar.getImage(), dataImage);
    }
    private boolean updateGrammar(){
        Grammar grammar = new Grammar();
        grammar.setIdGrammar(curGrammar.getIdGrammar());
        grammar.setTitle(txtareaTitle.getText());
        grammar.setImage(dataImage);
        grammar.setRule(txtareaRuleGrammar.getText());
        grammar.setContent(txtareaContent.getText());
        grammar.setExample(txtareaExampleGrammar.getText());
        return grammarDAO.update(grammar);
    }
    private void clearField(){
        txtareaTitle.setText("");
        txtareaContent.setText("");
        txtareaRuleGrammar.setText("");
        txtareaExampleGrammar.setText("");
        lblSrc.setText("");
        dataImage = null;
    }
}
