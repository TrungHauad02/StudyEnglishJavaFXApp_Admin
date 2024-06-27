package com.englishforadmin.controller;
import com.englishforadmin.GetResourceController;
import com.englishforadmin.MainApplication;
import com.englishforadmin.StateManager;
import com.englishforadmin.daoimpl.LessonPartDAO;
import com.englishforadmin.daoimpl.VocabularyDAO;
import com.englishforadmin.daoimpl.VocabularyPartDAO;
import com.englishforadmin.feature.MessageBox;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Volcabulary_newController {
    @FXML
    private MFXButton btnCancelNewVolcabulary;

    @FXML
    private MFXButton btnChooseImg;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMaximise;

    @FXML
    private Button btnMinimise;

    @FXML
    private MFXButton btnSubmitNewVolcabulary;

    @FXML
    private MFXButton btnUserInformation;

    @FXML
    private MFXButton btnVolcabulary01;

    @FXML
    private MFXButton btnVolcabulary02;

    @FXML
    private GridPane gridpnVolcabulary;

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
    private Pane pnTitleNewLesson;

    @FXML
    private TextArea txtMeaning;

    @FXML
    private TextField txtPhonetic;

    @FXML
    private TextField txtWord;
    @FXML
    private Label lblSrc;

    @FXML
    private TextArea txtareaAntonyms;

    @FXML
    private TextArea txtareaSynonyms;
    @FXML
    MFXButton btnEditSynonyms;
    @FXML
    MFXButton btnEditAntonyms;
    // fixing
    @FXML
    void SubmitVolcabulary_new(ActionEvent event ) throws IOException
    {
        if(isMissData()){
            MessageBox.show("Lỗi","Hãy điền đầy đủ thông tin trước khi tiếp tục", Alert.AlertType.ERROR);
            return;
        }
        if(addNewVocabulary()){
            loadData();
            clearField();
            MessageBox.show("Thành công","Thêm từ vựng thành công", Alert.AlertType.CONFIRMATION);
        }else {
            MessageBox.show("Lỗi","Thêm từ vựng không thành công", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void CancelVolcabulary_new(ActionEvent event ) throws IOException
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
    VocabularyDAO vocabularyDAO;
    List<Vocabulary> lstVocabulary;
    List<Vocabulary> lstSynonyms;
    List<Vocabulary> lstAntonyms;
    Vocabulary curVocabulary;
    byte[] dataImage;
    @FXML
    public void initialize() {
        lesson = StateManager.getCurrentLesson();
        vocabularyDAO = new VocabularyDAO();
        loadData();
        btnChooseImg.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image File");
            List<String> imageExtensions = Arrays.asList("*.jpg", "*.jpeg", "*.png", "*.gif");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", imageExtensions);
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog((Stage) ((Node) event.getSource()).getScene().getWindow());

            if (selectedFile != null) {
                try {
                    dataImage = convertImageToBase64(selectedFile);
                    lblSrc.setText(Arrays.toString(Base64.getDecoder().decode(dataImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnEditSynonyms.setOnAction(event -> {
            showSynonymsAntonymsForm(true);
        });
        btnEditAntonyms.setOnAction(event -> {
            showSynonymsAntonymsForm(false);
        });
    }
    private byte[] convertImageToBase64(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encode(fileContent);
    }
    private void loadData(){
        lstVocabulary = vocabularyDAO.selectBySql(VocabularyDAO.SELECT_ALL_VOCABULARY_IN_LESSON_QUERY, lesson.getIdLesson());
        loadGridPane();
    }
    private void loadGridPane(){
        gridpnVolcabulary.getChildren().clear();
        if(lstVocabulary.isEmpty())
            return;

        int numEntity = lstVocabulary.size();
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

                    gridpnVolcabulary.getChildren().add(button);
                    index++;
                }
            }
        }
    }

    private void loadDataToFeild(int index){
        curVocabulary = lstVocabulary.get(index);
        txtWord.setText(curVocabulary.getWord());
        txtMeaning.setText(curVocabulary.getMean());
        txtPhonetic.setText(curVocabulary.getPhonetic());
        dataImage = curVocabulary.getImage();
        if(dataImage!=null){
            lblSrc.setText(Arrays.toString(Base64.getDecoder().decode(dataImage)));
        }
        String id = curVocabulary.getIdVocabulary();
        curVocabulary.setSynonyms(vocabularyDAO.selectBySql(VocabularyDAO.SELECT_ALL_SYNONYMS_VOCABULARY_QUERY, id,id));
        curVocabulary.setAntonyms(vocabularyDAO.selectBySql(VocabularyDAO.SELECT_ALL_ANTONYMS_VOCABULARY_QUERY, id,id));
        lstAntonyms = curVocabulary.getAntonyms();
        lstSynonyms = curVocabulary.getSynonyms();
        updateSynonymsAntonyms();
    }

    private void showSynonymsAntonymsForm(boolean isSynonyms){
        FXMLLoader loader = new FXMLLoader(GetResourceController.getFXMLResourcePath("/com/englishforadmin/fxml/volcabulary/Add_Synonyms_Antonyms.fxml"));
        try {
            Parent root = loader.load();
            SynonymsAntonymsController controller = loader.getController();

            String word = curVocabulary != null ? curVocabulary.getWord() : txtWord.getText().isEmpty() ? "" : txtWord.getText();

            controller.setTitle(word, isSynonyms);
            Scene scene = new Scene(root, 899, 640);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Vocabulary");
            dialogStage.setScene(scene);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.showAndWait();
            if(isSynonyms){
                lstSynonyms = controller.getLstResult();
                updateSynonymsAntonyms();
            }else {
                lstAntonyms = controller.getLstResult();
                updateSynonymsAntonyms();
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateSynonymsAntonyms(){
        if (lstSynonyms != null){
            StringBuilder stringBuilder = new StringBuilder();
            for (Vocabulary vocabulary : lstSynonyms) {
                stringBuilder.append(vocabulary.getWord()).append(", ");
            }
            if (stringBuilder.length() > 2) {
                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            }
            txtareaSynonyms.setText(stringBuilder.toString());
        }
        if (lstAntonyms != null){
            StringBuilder stringBuilder2 = new StringBuilder();
            for (Vocabulary vocabulary : lstAntonyms) {
                stringBuilder2.append(vocabulary.getWord()).append(", ");
            }
            if (stringBuilder2.length() > 2) {
                stringBuilder2.delete(stringBuilder2.length() - 2, stringBuilder2.length());
            }
            txtareaAntonyms.setText(stringBuilder2.toString());
        }
    }
    private boolean isMissData(){
        return txtWord.getText().isEmpty()
                || txtMeaning.getText().isEmpty()
                || txtPhonetic.getText().isEmpty();
    }

    private boolean addNewVocabulary(){
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setWord(txtWord.getText());
        vocabulary.setMean(txtMeaning.getText());
        vocabulary.setPhonetic(txtPhonetic.getText());
        if(lstSynonyms != null && !lstSynonyms.isEmpty()){
            vocabulary.setSynonyms(lstSynonyms);
        }
        if(lstAntonyms != null && !lstAntonyms.isEmpty()){
            vocabulary.setAntonyms(lstAntonyms);
        }
        if(dataImage!=null){
            vocabulary.setImage(dataImage);
        }

        if (!vocabularyDAO.insert(vocabulary)){
            return false;
        }
        vocabulary.setIdVocabulary(vocabularyDAO.getLastestId());
        for (Vocabulary antonym : vocabulary.getAntonyms()) {
            vocabularyDAO.insertAntonyms(vocabulary.getIdVocabulary(), antonym.getIdVocabulary());
        }
        for (Vocabulary synonyms : lstSynonyms) {
            vocabularyDAO.insertSynonyms(vocabulary.getIdVocabulary(), synonyms.getIdVocabulary());
        }
        LessonPart lessonPart = new LessonPart();
        lessonPart.setIdLesson(StateManager.getCurrentLesson().getIdLesson());
        lessonPart.setType(LessonPart.LessonPartType.VOCABULARY);
        LessonPartDAO lessonPartDAO = new LessonPartDAO();
        if (!lessonPartDAO.insert(lessonPart)){
            return false;
        }
        VocabularyPart part = new VocabularyPart();
        part.setIdVocabulary(vocabulary.getIdVocabulary());
        part.setIdLessonPart(lessonPartDAO.getLastestId());
        VocabularyPartDAO dao = new VocabularyPartDAO();
        return dao.insert(part);
    }

    private void clearField(){
        txtWord.setText("");
        txtMeaning.setText("");
        txtPhonetic.setText("");
        txtareaSynonyms.setText("");
        txtareaAntonyms.setText("");
        if(lstAntonyms != null){
            lstAntonyms.clear();
        }
        if(lstSynonyms != null){
            lstSynonyms.clear();
        }
        dataImage = null;
    }
}
