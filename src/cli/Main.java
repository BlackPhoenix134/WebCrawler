package cli;

import crawler.data.CrawlResult;
import crawler.data.JsoupWebProcessor;
import crawler.data.WebCrawler;
import crawler.log.Logger;
import org.apache.commons.cli.*;
import org.jsoup.Jsoup;

import java.io.File;
import java.util.Arrays;

public class Main {
    private static CommandLineArgs cliArgs = new CommandLineArgs();


    public static void main(String[] args) {
        Logger.setLogFunction(System.out::println);
        HelpFormatter helpFormatter = new HelpFormatter();
        Logger.out("Executing args: " + Arrays.toString(args));
        try {
            cliArgs.parse(args);
            Logger.setLogLevel(cliArgs.getLogLevel());
            Thread crawlThread = new Thread(new WebCrawler(cliArgs.getStartUrls(),  new JsoupWebProcessor(), cliArgs.getDepth(),
                (crawlResult) -> {
                    crawlResult.addDataFormatter(cliArgs.getFormatters());
                    printCrawlResult(crawlResult);
            }));
            crawlThread.start();
        } catch (ParseException e) {
            helpFormatter.printHelp("webcrawler", cliArgs.getOptions());
        }
    }

    private static void printCrawlResult(CrawlResult result) {
        Logger.out(result.getFormattedData());
        if(cliArgs.getOutFilePath() != null)
            writeResultStatistics(result, cliArgs.getOutFilePath());
    }

    private static void writeResultStatistics(CrawlResult result, String path) {
        File file = new File(path);
    }

    public static void writeLine(String line) {
        System.out.println(line);
    }
}
