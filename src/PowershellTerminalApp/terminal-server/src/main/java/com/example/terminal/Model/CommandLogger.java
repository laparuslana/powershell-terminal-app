package com.example.terminal.Model;

import com.example.terminal.DatabaseAccess.CommandRepository;
import com.example.terminal.DatabaseAccess.CommandEntry;

import java.sql.SQLException;
import java.sql.Timestamp;

public class CommandLogger {
    private final CommandRepository commandRepository;

    public CommandLogger(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }


    public void logCommandToDatabase(String command, String description) {
        CommandEntry commandEntry = new CommandEntry();
        commandEntry.setCommandText(command);
        commandEntry.setDescription(description);
        commandEntry.setExecutionTime(new Timestamp(System.currentTimeMillis()));
        try {
            commandRepository.insertCommand(commandEntry);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

