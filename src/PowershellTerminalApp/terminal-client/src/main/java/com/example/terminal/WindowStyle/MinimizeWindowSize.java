package com.example.terminal.WindowStyle;


import javafx.stage.Stage;

public class MinimizeWindowSize implements WindowSize {
    @Override
    public void setWindowSize(Stage stage, double width, double height) {
        stage.setWidth(stage.getWidth() / 2);
        stage.setHeight(stage.getHeight() / 2);
    }
}

