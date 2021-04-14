package cli;

import org.apache.commons.cli.*;

public class CommandLineArgs {
    private final Options options = new Options();
    private final CommandLineParser parser = new DefaultParser();

    private String startUrl;
    private int depth = 2;
    private int logLevel = 3;

    public Options getOptions() {
        return options;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public int getDepth() {
        return depth;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public CommandLineArgs() {
        Option urlOpt = new Option("u", "url", true, "input start url");
        urlOpt.setRequired(true);
        options.addOption(urlOpt);

        Option depthOpt = new Option("d", "depth", true, "set crawl depth");
        depthOpt.setRequired(false);
        options.addOption(depthOpt);

        Option logLevelOpt = new Option("ll", "logLevel", true, "set log level depth");
        logLevelOpt.setRequired(false);
        options.addOption(logLevelOpt);
    }

    public void parse(String[] args) throws ParseException {
        CommandLine cmd;
        cmd = parser.parse(options, args);
        startUrl = cmd.getOptionValue("u");
        if(cmd.hasOption("d"))
            depth = Integer.parseInt(cmd.getOptionValue("d"));
        if(cmd.hasOption("ll"))
            logLevel = Integer.parseInt(cmd.getOptionValue("ll"));
    }
}
