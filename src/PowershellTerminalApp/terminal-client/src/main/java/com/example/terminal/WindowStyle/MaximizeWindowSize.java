package com.example.terminal.WindowStyle;


import javafx.stage.Stage;

public class MaximizeWindowSize implements WindowSize {
    @Override
    public void setWindowSize(Stage stage, double width, double height) {
        stage.setMaximized(true);
    }
}
