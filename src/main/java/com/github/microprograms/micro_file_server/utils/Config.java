package com.github.microprograms.micro_file_server.utils;

import org.apache.commons.cli.CommandLine;

public class Config {
    private static CommandLine commandLine;

    public static CommandLine getCommandLine() {
        return commandLine;
    }

    public static void setCommandLine(CommandLine commandLine) {
        Config.commandLine = commandLine;
    }
}
