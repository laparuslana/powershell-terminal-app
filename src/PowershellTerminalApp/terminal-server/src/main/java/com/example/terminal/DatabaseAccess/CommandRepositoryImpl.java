package com.example.terminal.DatabaseAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CommandRepositoryImpl implements CommandRepository {
    private final Connection connection;

    public CommandRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    // Method to insert a new command entry into the database
    @Override
    public void insertCommand(CommandEntry commandEntry) throws SQLException {
        String sql = "INSERT INTO commandhistory (commandText, description, executionTime) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, commandEntry.getCommandText());
            statement.setString(2, commandEntry.getDescription());
            statement.setTimestamp(3, commandEntry.getExecutionTime());
            statement.executeUpdate();
        }
    }

    @Override
    public List<CommandEntry> getCommandHistory() {
        List<CommandEntry> commandHistory = new ArrayList<>();

        try {
            // Create a SQL query to retrieve all command history records
            String sql = "SELECT * FROM commandHistory";

            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Iterate through the result set and create CommandEntry objects
            while (resultSet.next()) {
                CommandEntry entry = new CommandEntry();
                entry.setCommandText(resultSet.getString("commandText"));
                entry.setDescription(resultSet.getString("description"));
                entry.setExecutionTime(resultSet.getTimestamp("executionTime"));
                commandHistory.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandHistory;
    }

    @Override
    public void clearCommandHistory() {
        try {
            String sql = "DELETE FROM commandhistory";

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}