package com.github.microprograms.micro_file_server.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MicroFileServer {
    private static final Logger log = LoggerFactory.getLogger(MicroFileServer.class);

    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("h", "help", false, "Print this usage information");
        options.addOption("p", "port", true, "Port");
        options.addOption("l", "localStoragePath", true, "Local storage path");
        options.addOption("f", "urlFormat", true, "URL format");
        CommandLine commandLine = parser.parse(options, args);
        if (commandLine.hasOption('h')) {
            System.out.println("Help Message");
            System.exit(0);
        }
        if (!commandLine.hasOption('p')) {
            System.out.println("Missing port parameter");
            System.exit(0);
        }
        if (!commandLine.hasOption('l')) {
            System.out.println("Missing localStoragePath parameter");
            System.exit(0);
        }
        if (!commandLine.hasOption('f')) {
            System.out.println("Missing urlFormat parameter");
            System.exit(0);
        }
        Config.setCommandLine(commandLine);
        log.info("Command line args: {}", StringUtils.join(commandLine.getArgs(), " "));
        String port = commandLine.getOptionValue('p');
        Server server = new Server(Integer.parseInt(port));
        server.setHandler(new HttpRequestHandler());
        server.start();
        server.join();
    }
}
