package com.example.terminal.Model;

public class ScriptFileCommand implements Command {
    private final CommandExecutorService executorService;
    private final String scriptFilePath;
    private String result;

    public ScriptFileCommand(String scriptFilePath, CommandExecutorService executorService) {
        this.executorService = executorService;
        this.scriptFilePath = scriptFilePath;
    }

    @Override
    public void execute() {
        executorService.executeScriptFile(scriptFilePath);
        result = executorService.getResult();
    }
    @Override
    public String getResult() {
        return result;
    }
}

