package com.englishforadmin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class NavigationManager {
    private static NavigationManager instance;
    private Stage mainStage;
    private String previousPage;

    private static Scene previousScene;
    private NavigationManager() {}

    public static NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    // Getter and setter for previousScene
    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void loadForm( String fxmlDirectory ,String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/englishforadmin/fxml" + fxmlDirectory + "/" + fxmlFile));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainApplication.class.getResource("/meta-style.css").toExternalForm());


        if (mainStage != null) {
            mainStage.setScene(scene);

            mainStage.show();
        } else {
            System.out.println("mainStage is null. Please initialize it before calling loadForm.");
        }
    }

    public void setPreviousPage(String page) {
        previousPage = page;
    }

    public String getPreviousPage() {
        return previousPage;
    }
}
