package cli;

import crawler.statistics.DefaultFormatter;
import crawler.statistics.Formatter;
import org.apache.commons.cli.*;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CommandLineArgs {
    private final Options options = new Options();
    private final CommandLineParser parser = new DefaultParser();

    private List<String> startUrls = new ArrayList<>();
    private int depth = 2;
    private int logLevel = 3;
    private String outFilePath = null;
    private List<Formatter> formatters = new ArrayList<>();

    public Options getOptions() {
        return options;
    }

    public List<String> getStartUrls() {
        return startUrls;
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

    public List<Formatter> getFormatters() {
        return formatters;
    }

    public CommandLineArgs() {
        Option urlOpt = new Option("u", "url",  true, "input urls");
        urlOpt.setArgs(Option.UNLIMITED_VALUES);
        urlOpt.setRequired(true);
        urlOpt.setValueSeparator(',');
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

        Option formattersOpt = new Option("f", "formatter", true,
                String.format("Formatter [%s],[%s],[%s]", CliFormatterTable.ID_DEFAULT,
                        CliFormatterTable.ID_NOTFOUND, CliFormatterTable.ID_VISITED));
        formattersOpt.setArgs(Option.UNLIMITED_VALUES);
        formattersOpt.setValueSeparator(',');
        options.addOption(formattersOpt);
    }

    public void parse(String[] args) throws ParseException {
        CommandLine cmd;
        cmd = parser.parse(options, args);
        startUrls = parseUrls("u");
        if(cmd.hasOption("d"))
            depth = Integer.parseInt(cmd.getOptionValue("d"));
        if(cmd.hasOption("ll"))
            logLevel = Integer.parseInt(cmd.getOptionValue("ll"));
        if(cmd.hasOption("o"))
            outFilePath = getAbsolutePath(cmd.getOptionValue("o"));
        if(cmd.hasOption("f"))
            formatters = CliFormatterTable.parse(cmd.getOptionValues("f"));
        else
            formatters.add(new DefaultFormatter());

    }

    private List<String> parseUrls(String urls) {
        return Arrays.asList(urls.split(","));
    }

    private String getAbsolutePath(String path) {
        return FileSystems.getDefault().getPath(path).normalize().toAbsolutePath().toString();
    }
}
