package com.englishforadmin.controller;

import com.englishforadmin.daoimpl.VocabularyDAO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Vocabulary;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class SynonymsAntonymsController {
    @FXML
    Label lblTitle;
    @FXML
    MFXTextField txtVocabulary;
    @FXML
    MFXFilterComboBox<Vocabulary> cbVocabulary;
    @FXML
    MFXButton btnAdd;
    @FXML
    TableView<Vocabulary> tbvVocabulary;
    @FXML
    TableColumn<Integer, String> tbcWord;
    @FXML
    TableColumn<Vocabulary, String> tbcDelete;
    @FXML
    MFXButton btnSubmit;

    private ObservableList<Vocabulary> lstVocabulary;
    private ObservableList<Vocabulary> lstResult;
    VocabularyDAO vocabularyDAO;
    Vocabulary curVocabulary;
    public void initialize() {
        vocabularyDAO = new VocabularyDAO();
        List<Vocabulary> allVocabulary = vocabularyDAO.selectAll();
        lstVocabulary = FXCollections.observableArrayList(allVocabulary);
        cbVocabulary.setItems(lstVocabulary);
        cbVocabulary.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            curVocabulary = newValue;
            btnAdd.setDisable(false);
        });
        cbVocabulary.setConverter(new StringConverter<Vocabulary>() {
            @Override
            public String toString(Vocabulary vocabulary) {
                return vocabulary == null ? "" : vocabulary.getWord();
            }

            @Override
            public Vocabulary fromString(String string) {
                return null;
            }
        });
        btnAdd.setOnAction(event -> {
            if (curVocabulary != null) {
                lstResult.add(curVocabulary);
                lstVocabulary.remove(curVocabulary);
                refreshTableView();
                curVocabulary = null;
                cbVocabulary.getSelectionModel().clearSelection();
            }
        });
        lstResult = FXCollections.observableArrayList();
        tbcWord.setCellValueFactory(new PropertyValueFactory<>("word"));
        tbcDelete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            {
                deleteButton.setOnAction(event -> {
                    Vocabulary vocabulary = getTableView().getItems().get(getIndex());
                    lstResult.remove(vocabulary);
                    lstVocabulary.add(vocabulary);
                    curVocabulary = vocabulary;
                    cbVocabulary.getSelectionModel().selectItem(vocabulary);
                    refreshTableView();
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
        btnSubmit.setOnAction(event -> {
            Node sourceNode = (Node) event.getSource();
            Scene scene = sourceNode.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();
        });
    }

    public void setTitle(String vocabulary, boolean isSynonyms){
        txtVocabulary.setText(vocabulary);
        lblTitle.setText(isSynonyms ? "Synonyms" : "Antonyms");
    }
    private void refreshTableView() {
        tbvVocabulary.getItems().setAll(lstResult);
    }

    public List<Vocabulary> getLstResult(){
        return new ArrayList<>(lstResult);
    }
}
