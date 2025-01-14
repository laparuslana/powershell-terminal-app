package com.example.terminal.WindowStyle;


import javafx.scene.Parent;

public class DarkTheme implements BackgroundTheme {
    @Override
    public void applyBackground(Parent root) {
        root.setStyle("-fx-background-color: #222222;");
    }
}

