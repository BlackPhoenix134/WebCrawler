package cli;

import crawler.data.CrawlResult;
import crawler.data.WebCrawler;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
	// write your code here
        WebCrawler crawler = new WebCrawler("https://jsoup.org/cookbook/input/load-document-from-url");
        CrawlResult result = crawler.start();
    }
}
