package com.example.terminal.WindowStyle;


import javafx.scene.Parent;

public class HighContrastTheme implements BackgroundTheme {
    @Override
    public void applyBackground(Parent root) {
        root.setStyle("-fx-background-color: #000000;");
    }
}

