package com.example.terminal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ClientApp extends Application {

@Override
public void start(Stage primaryStage) throws Exception {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
    Parent root = loader.load();

    RemoteController controller = loader.getController();
    controller.setServerUrl("http://localhost:55555");
    primaryStage.setOnCloseRequest(event -> controller.onCloseWindow());

    primaryStage.setTitle("Terminal App");
    primaryStage.setScene(new Scene(root, 800, 600));
    primaryStage.show();
}

    public static void main(String[] args) {
        launch(args);
    }
}
