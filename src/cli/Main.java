package cli;

import crawler.data.CrawlResult;
import crawler.data.WebCrawler;
import crawler.log.Logger;
import org.apache.commons.cli.*;
import org.w3c.dom.xpath.XPathResult;

public class Main {

    public static void main(String[] args) {
        Logger.setLogFunction(System.out::println);
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLineArgs cliArgs = new CommandLineArgs();
        try {
            cliArgs.parse(args);
            Logger.setLogLevel(cliArgs.getLogLevel());
            WebCrawler crawler = new WebCrawler(cliArgs.getStartUrl());
            CrawlResult result = crawler.start(cliArgs.getDepth());
        } catch (ParseException e) {
            helpFormatter.printHelp("webcrawler", cliArgs.getOptions());
        }


    }

    public static void writeLine(String line) {
        System.out.println(line);
    }
}
