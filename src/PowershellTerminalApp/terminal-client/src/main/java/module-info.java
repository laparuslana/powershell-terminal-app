
module terminal.client {
    requires javafx.graphics;
    requires javafx.controls;
    requires java.net.http;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.terminal;
    opens com.example.terminal to javafx.fxml;
    exports com.example.terminal.WindowStyle;
    opens com.example.terminal.WindowStyle to javafx.fxml;
}