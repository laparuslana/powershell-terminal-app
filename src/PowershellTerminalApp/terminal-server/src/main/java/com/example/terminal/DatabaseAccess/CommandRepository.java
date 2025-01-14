package com.example.terminal.DatabaseAccess;

import java.sql.SQLException;
import java.util.List;

public interface CommandRepository {
    void insertCommand(CommandEntry commandEntry) throws SQLException;

    List<CommandEntry> getCommandHistory();

    void clearCommandHistory();


}
