package com.example.terminal.SyntaxStyle;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
public abstract class Syntax {
    public final void highlightSyntax(String output, TextFlow textFlow) {
        String[] lines = splitOutput(output);
        for (String line : lines) {
            String[] words = line.split(" ");

            for (String word : words) {
                Text text = new Text(word + " ");

                highlight(word, text, textFlow);
            }
            textFlow.getChildren().add(new Text("\n"));
        }
        displayText(textFlow);
    }

    private String[] splitOutput(String output) {
        return output.split("\n");
    }

    private void displayText(TextFlow textFlow) {
        Text separator = new Text("\n");
        separator.setFill(Color.BLACK);
        textFlow.getChildren().add(separator);

    }
    protected abstract void highlight(String word, Text text, TextFlow textFlow);
}
