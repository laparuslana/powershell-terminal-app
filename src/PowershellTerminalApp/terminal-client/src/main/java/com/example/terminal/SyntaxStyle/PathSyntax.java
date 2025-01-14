package com.example.terminal.SyntaxStyle;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PathSyntax extends Syntax {
    @Override
    protected void highlight(String word, Text text, TextFlow textFlow) {

        if (word.matches("[A-Z]:\\\\.*")) {
            text.setFill(Color.GREEN);
        }

        textFlow.getChildren().add(text);
    }
}

