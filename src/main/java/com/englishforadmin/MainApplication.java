package com.englishforadmin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class MainApplication extends Application {
    private static Stage mainStage;
    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    private static Scene previousScene;
    public static void setPreviousScene(Scene scene) {
        previousScene = scene;
    }
    public static Scene getPreviousScene() {
        return previousScene;
    }

    @Override
    public void start(Stage stage) throws IOException {
        NavigationManager navigationManager = NavigationManager.getInstance();
        navigationManager.setMainStage(stage);
        mainStage = stage;
        loadForm("", "main.fxml");
        mainStage.setTitle("English application management for administrator");
        mainStage.show();
    }

    public static void loadForm(String fxmlDirectory, String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/englishforadmin/fxml" + fxmlDirectory + "/" + fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 900, 640);

        scene.getStylesheets().add(MainApplication.class.getResource("/meta-style.css").toExternalForm());


        MainApplication.setPreviousScene(mainStage.getScene()); // Cập nhật scene trước đó
        if (mainStage != null) {
            mainStage.setScene(scene);
        } else {
            System.out.println("mainStage is null. Please initialize it before calling loadForm.");
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
