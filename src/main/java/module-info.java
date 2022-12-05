module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.demo to javafx.fxml;
    exports application;
    exports FXML;
    exports gui;
    opens FXML;
    opens application to javafx.fxml;
}