package cli;

import crawler.data.CrawlResult;
import crawler.data.WebCrawler;
import crawler.log.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
	    //write your code here
        Logger.setLogFunction(System.out::println);
        WebCrawler crawler = new WebCrawler("https://jsoup.org/cookbook/input/load-document-from-url");
        CrawlResult result = crawler.start();
    }

    public static void writeLine(String line) {
        System.out.println(line);
    }
}
