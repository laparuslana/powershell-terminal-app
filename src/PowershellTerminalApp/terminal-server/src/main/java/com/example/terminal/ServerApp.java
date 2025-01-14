package com.example.terminal;

import com.example.terminal.DatabaseAccess.CommandEntry;
import com.example.terminal.DatabaseAccess.CommandRepository;
import com.example.terminal.DatabaseAccess.CommandRepositoryImpl;
import com.example.terminal.DatabaseAccess.DatabaseManager;
import com.example.terminal.Model.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;


public class ServerApp {
    private static final CommandInvoker commandInvoker= new CommandInvoker();
    private static final DatabaseManager databaseManager = new DatabaseManager();
    private static final CommandRepository commandRepository;
    static {
        try {
            commandRepository = new CommandRepositoryImpl(databaseManager.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private  static  final CommandExecutorService executorService= new CommandExecutorService(commandRepository);



    public static void main(String[] args) {
        Javalin app = Javalin.create().start(55555);
        app.delete("/close-window", ServerApp ::closeWindowHandler);
        app.get("/fetch-command-history", ServerApp::fetchCommandHistoryHandler);
        app.post("/execute-command", ServerApp::executeHandler);

    }

    private static void closeWindowHandler(Context ctx) {
        try {
            commandRepository.clearCommandHistory();
            databaseManager.closeConnection();

            ctx.result("Command history cleared successfully");
        } catch (SQLException e) {
            ctx.status(500).result("Internal Server Error");
            e.printStackTrace();
        }
    }

    private static void executeHandler(Context ctx) {
        String commandText = ctx.body();
        String output;

        if (isDateFormatCommand(commandText)) {
            String dateCommand = commandText.substring(4).trim();

            Format format = new Format();
            DateFormatInterpreter dateFormatInterpreter = new DateFormatInterpreter();
            dateFormatInterpreter.interpret(dateCommand, format);
            output = format.getFormat();
        } else {
            Command commandObject;
            if (commandText.toLowerCase().endsWith(".ps1")) {
                commandObject = new ScriptFileCommand(commandText, executorService);
            } else {
                commandObject = new PowerShellCommand(commandText, executorService);
            }
            commandInvoker.execute(commandObject);
            output = commandObject.getResult();
        }
        ctx.result(output);
    }

    private static boolean isDateFormatCommand(String commandText) {
        return commandText.toLowerCase().startsWith("date");
    }

    private static void fetchCommandHistoryHandler(Context ctx) {
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            CommandRepository commandRepository = new CommandRepositoryImpl(databaseManager.getConnection());

            List<CommandEntry> commandHistory = commandRepository.getCommandHistory();

            commandHistory.forEach(entry -> {
                System.out.println("Fetched Command: " + entry.getCommandText());
            });

            String commandHistoryString = commandHistory.stream()
                    .map(entry -> entry.getCommandText() + "\n")
                    .collect(Collectors.joining());

            ctx.result(commandHistoryString);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

