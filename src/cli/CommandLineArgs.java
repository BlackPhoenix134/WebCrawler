package cli;

import org.apache.commons.cli.*;

import java.nio.file.FileSystems;


public class CommandLineArgs {
    private final Options options = new Options();
    private final CommandLineParser parser = new DefaultParser();

    private String startUrl;
    private int depth = 2;
    private int logLevel = 3;
    private String outFilePath = null;

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

    public String getOutFilePath() {
        return outFilePath;
    }

    public CommandLineArgs() {
        Option urlOpt = new Option("u", "url", true, "input start url");
        urlOpt.setRequired(true);
        options.addOption(urlOpt);

        Option depthOpt = new Option("d", "depth", true, "set crawl depth");
        depthOpt.setRequired(false);
        options.addOption(depthOpt);

        Option outFileOpt = new Option("o", "outfile", true, "path to output file");
        outFileOpt.setRequired(false);
        options.addOption(outFileOpt);

        Option logLevelOpt = new Option("ll", "loglevel", true, "set log level depth");
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
        if(cmd.hasOption("o"))
            outFilePath = getAbsolutePath(cmd.getOptionValue("o"));
    }

    private String getAbsolutePath(String path) {
        return FileSystems.getDefault().getPath(path).normalize().toAbsolutePath().toString();
    }
}
