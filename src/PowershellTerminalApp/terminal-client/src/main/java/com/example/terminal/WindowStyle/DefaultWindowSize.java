package com.example.terminal.WindowStyle;

import javafx.stage.Stage;

public class DefaultWindowSize implements WindowSize {
    @Override
    public void setWindowSize(Stage stage, double width, double height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }
}
