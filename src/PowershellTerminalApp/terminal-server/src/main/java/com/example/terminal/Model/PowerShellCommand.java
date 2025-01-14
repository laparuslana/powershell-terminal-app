package com.example.terminal.Model;

public class PowerShellCommand implements Command {
    private final CommandExecutorService executorService;
    private final String command;
    private String result;

    public PowerShellCommand(String command, CommandExecutorService executorService) {
        this.executorService = executorService;
        this.command = command;
    }

    @Override
    public void execute() {
        executorService.executePowershellCommand(command);
        result = executorService.getResult();
    }

    @Override
    public String getResult() {
        return result;
    }
}

