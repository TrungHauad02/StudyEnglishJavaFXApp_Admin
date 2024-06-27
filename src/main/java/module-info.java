module com.englishforadmin {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires MaterialFX;

    opens com.englishforadmin to javafx.fxml;
    exports com.englishforadmin;
    exports com.englishforadmin.controller;
    opens com.englishforadmin.controller to javafx.fxml;
    opens model to javafx.base;

}