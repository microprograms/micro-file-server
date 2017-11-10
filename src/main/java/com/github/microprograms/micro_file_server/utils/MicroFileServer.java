package com.github.microprograms.micro_file_server.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class MicroFileServer {
    private static final Logger log = LoggerFactory.getLogger(MicroFileServer.class);
    private static final Config config = new Config();

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
        config.setLocalStoragePath(commandLine.getOptionValue('l'));
        config.setLocalTempPath(System.getProperty("java.io.tmpdir"));
        config.setUrlFormat(commandLine.getOptionValue('f'));
        log.info("parsed config: {}", JSON.toJSONString(config));
        String port = commandLine.getOptionValue('p');
        Server server = new Server(Integer.parseInt(port));
        server.setHandler(new HttpRequestHandler());
        server.start();
        server.join();
    }

    public static Config getConfig() {
        return config;
    }
}
