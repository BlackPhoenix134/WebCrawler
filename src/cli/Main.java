package cli;

import crawler.data.CrawlResult;
import crawler.data.WebCrawler;
import crawler.log.Logger;
import org.apache.commons.cli.*;
import org.w3c.dom.xpath.XPathResult;

import java.io.File;

public class Main {
    private static CommandLineArgs cliArgs = new CommandLineArgs();


    public static void main(String[] args) {
        Logger.setLogFunction(System.out::println);
        HelpFormatter helpFormatter = new HelpFormatter();
        try {
            cliArgs.parse(args);
            Logger.setLogLevel(cliArgs.getLogLevel());
            WebCrawler crawler = new WebCrawler(cliArgs.getStartUrl());
            CrawlResult result = crawler.start(cliArgs.getDepth());
            printDoneMessage(result);
        } catch (ParseException e) {
            helpFormatter.printHelp("webcrawler", cliArgs.getOptions());
        }
    }

    private static void printDoneMessage(CrawlResult result) {
        Logger.out("Crawling Finished");
        Logger.out("Words: " + result.getReadableTextCount());
        Logger.out("404: " + result.getNotFoundUrls().size());
        Logger.out("Visited Pages: " + result.getVisitedPages());

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
