package com.example.terminal.SyntaxStyle;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class KeywordSyntax extends Syntax {
    @Override
    protected void highlight(String word, Text text, TextFlow textFlow) {

        if (word.equals("if") || word.equals("else") || word.equals("elseif")) {
            text.setFill(Color.RED);
        }

        textFlow.getChildren().add(text);
    }
}

