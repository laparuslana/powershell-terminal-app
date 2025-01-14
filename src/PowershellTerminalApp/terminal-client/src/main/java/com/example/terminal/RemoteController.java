package com.example.terminal;

import com.example.terminal.SyntaxStyle.PathSyntax;
import com.example.terminal.SyntaxStyle.Syntax;
import com.example.terminal.WindowStyle.BackgroundTheme;
import com.example.terminal.WindowStyle.DefaultWindowSize;
import com.example.terminal.WindowStyle.ThemeManager;
import com.example.terminal.WindowStyle.WindowSize;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class RemoteController {

    private final HttpClient client = HttpClient.newHttpClient();
    private String serverUrl;

    @FXML
    private TextFlow textFlow1;

    @FXML
    private TextFlow textFlow2;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ComboBox<String> strategyComboBox;

    @FXML
    private TextField commandTextField1;

    @FXML
    private TextField commandTextField2;


    @FXML
    private void initialize() {
        scrollPane = new ScrollPane();
        commandHistory();
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    //????
    @FXML
    private void createNewWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
            Parent newWindowContent = loader.load();

            Stage newWindow = new Stage();
            newWindow.setScene(new Scene(newWindowContent));
            newWindow.setTitle("New Window");
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void applyBackgroundColor(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        Parent root = scene.getRoot();
        String selectedScheme = strategyComboBox.getValue();

        BackgroundTheme scheme = ThemeManager.getFactory(selectedScheme);

        if (scheme != null) {
            scheme.applyBackground(root);
        }
    }

    @FXML
    private void changeWindowSize(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowSize setSize = new DefaultWindowSize();
        //WindowSize setSize = new MaximizeWindowSize();
        //WindowSize setSize = new MinimizeWindowSize();
        setSize.setWindowSize(currentStage, 800, 600);
    }


    @FXML
    private void splitWindows(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/split-windows.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root, 800, 600));
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void returnToSingleWindow(ActionEvent event) {
        try {
            Parent originalLayout = FXMLLoader.load(getClass().getResource("/sample.fxml"));

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setScene(new Scene(originalLayout, 800, 600));
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void executeCommand1() {
        String commandText = commandTextField1.getText();

        if (commandText.trim().isEmpty()) {
            return;
        }

        executeDateFormatCommandLogic(commandText, textFlow1);
    }

    //????
    @FXML
    private void executeCommand2() {
        String commandText = commandTextField2.getText();

        if (commandText.trim().isEmpty()) {
            return;
        }

        executeDateFormatCommandLogic(commandText, textFlow2);
    }

    private void executeDateFormatCommandLogic(String commandText, TextFlow textFlow) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl + "/execute-command"))
                    .header("Content-Type", "text/plain")
                    .POST(HttpRequest.BodyPublishers.ofString(commandText))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String commandOutput = response.body();

            Syntax pathSyntax = new PathSyntax();
            pathSyntax.highlightSyntax(commandOutput, textFlow);

            scrollPane.setVvalue(1.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void commandHistory() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl + "/fetch-command-history"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String commandHistory = response.body();

            updateTextFlow(commandHistory, textFlow1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTextFlow(String commandHistory, TextFlow textFlow) {
        textFlow.getChildren().clear();

        String[] commands = commandHistory.split("\n");

        for (String command : commands) {
            Text commandText = new Text(command + "\n");
            textFlow.getChildren().add(commandText);
        }

        scrollPane.setVvalue(1.0);
    }

    public void onCloseWindow() {
        closeWindow();
    }

    private void closeWindow() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl + "/close-window"))
                    .DELETE()
                    .build();

            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
