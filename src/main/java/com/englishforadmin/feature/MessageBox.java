package com.englishforadmin.feature;

import javafx.scene.control.Alert;

public class MessageBox {
    public static void show(String title, String content, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
