package com.example.terminal.DatabaseAccess;

import java.sql.Timestamp;

public class CommandEntry {
    private String commandText;
    private String description;
    private Timestamp executionTime;

    public CommandEntry() {
    }

    public CommandEntry(String commandText, String description, Timestamp executionTime) {
        this.commandText = commandText;
        this.description = description;
        this.executionTime = executionTime;
    }

    public String getCommandText() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Timestamp executionTime) {
        this.executionTime = executionTime;
    }
}