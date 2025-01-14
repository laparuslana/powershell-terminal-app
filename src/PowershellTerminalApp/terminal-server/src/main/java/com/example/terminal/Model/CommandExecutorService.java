package com.example.terminal.Model;

import com.example.terminal.DatabaseAccess.CommandRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public class CommandExecutorService {
    private final CommandRepository commandRepository;
    private String result;
    public CommandExecutorService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public void executeScriptFile(String scriptFileName) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-NoProfile",
                    "-ExecutionPolicy", "Bypass", "-Command", scriptFileName);
            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("IBM866")));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();

            CommandLogger commandLogger = new CommandLogger(commandRepository);
            commandLogger.logCommandToDatabase(scriptFileName, output.toString());


            result = output.toString();
            System.out.println("Script file executed successfully.");
        } catch (IOException | InterruptedException e) {
            result = "Error executing Script file: " + e.getMessage();
            System.err.println(result);
        }
    }

    public void executePowershellCommand(String command) {

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-NoProfile",
                    "-ExecutionPolicy", "Bypass", "-Command", command);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("IBM866")));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();

            CommandLogger commandLogger = new CommandLogger(commandRepository);
            commandLogger.logCommandToDatabase(command, output.toString());

            result = output.toString();
            System.out.println("PowerShell command executed successfully.");
        } catch (IOException | InterruptedException e) {
            result = "Error executing PowerShell command: " + e.getMessage();
            System.err.println(result);
        }
    }

    public String getResult() {
        return result;
    }
}
