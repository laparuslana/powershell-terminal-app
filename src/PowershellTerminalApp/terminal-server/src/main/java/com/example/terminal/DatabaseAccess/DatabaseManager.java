package com.example.terminal.DatabaseAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Set up the connection to your database
            String url = "jdbc:mysql://localhost/terminal";
            String user = "root";
            String password = "";

            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
