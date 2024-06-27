package com.englishforadmin.controller;

import com.englishforadmin.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Lesson_newB2Controller {

    @FXML
    private MFXButton btnCancelNewLesson;

    @FXML
    private MFXButton btnEditGrammar;

    @FXML
    private MFXButton btnEditListening;

    @FXML
    private MFXButton btnEditSpeaking;

    @FXML
    private MFXButton btnEditVolcabulary;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMaximise;

    @FXML
    private Button btnMinimise;

    @FXML
    private MFXButton btnNewGrammar;

    @FXML
    private MFXButton btnNewListening;

    @FXML
    private MFXButton btnNewSpeaking;

    @FXML
    private MFXButton btnNewVolcabulary;

    @FXML
    private MFXButton btnSubmitNewLesson;

    @FXML
    private Label lblNumberGrammar;

    @FXML
    private Label lblNumberListening;

    @FXML
    private Label lblNumberSpeaking;

    @FXML
    private Label lblNumberVolcabulary;

    @FXML
    private Pane pnGrammarLesson;

    @FXML
    private Pane pnLessonContent;

    @FXML
    private Pane pnListeningLesson;

    @FXML
    private Pane pnMain;

    @FXML
    private Pane pnNewLesson;

    @FXML
    private Pane pnSpeakingLesson;

    @FXML
    private Pane pnTitleNewLesson;

    @FXML
    private Pane pnVolcabularyLesson;

    // listening :
    @FXML
    void addNewListeningScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/listening", "Listening_new.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void editListeningScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/listening", "Listening_edit.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // speaking
    @FXML
    void addNewSpeakingScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/speaking", "Speaking_new.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void editSpeakingScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/speaking", "Speaking_edit.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //grammar :
    @FXML
    void addNewGrammarScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/grammar", "Grammar_new.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void editGrammarScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/grammar", "Grammar_edit.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // volcabulary:
    @FXML
    void addNewVolcabularyScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/volcabulary", "Volcabulary_new.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void editVolcabularyScreen(ActionEvent event ) throws IOException
    {
        try {
            MainApplication.loadForm("/volcabulary", "Volcabulary_edit.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // fixing
    @FXML
    void SubmitLesson_newB2(ActionEvent event ) throws IOException
    {
        // nhảy lại form list lesson
        // load lại list lesson + thực hiện hàm new lesson procedure --

        try {
            MainApplication.loadForm("", "main.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CancelLesson_NewB2(ActionEvent event ) throws IOException
    {
        // nhảy lại form list lesson ==  main

        try {
            MainApplication.loadForm("", "main.fxml");
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

    }
}
