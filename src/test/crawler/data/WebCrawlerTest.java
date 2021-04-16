package crawler.data;

import crawler.data.CrawlResult;
import crawler.data.PageCrawlResult;
import crawler.data.WebCrawler;
import crawler.log.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class WebCrawlerTest {

    private final String URL = "https://jsoup.org/cookbook/input/load-document-from-url";
    private final String URL2 = "https://www.aau.at";
    private Document TEST_DOC;
    private WebCrawler webCrawler;
    private WebCrawler webCrawler2;
    private WebCrawler webCrawler3;
    private Map<String, List<Element>> exampleMap = new HashMap<>();


    @Before
    public void init() throws IOException {
        // needs to be set, otherwise it throws an exception.
        Logger.setLogFunction(System.out::println);
        TEST_DOC = Jsoup.connect(URL).get();
        webCrawler = new WebCrawler(URL);
        webCrawler2 = new WebCrawler(URL);
        webCrawler3 = new WebCrawler(URL2);
    }
    @After
    public void destroy() {
        webCrawler = null;
    }
    @Test
    public void checkCrawlResult() {
        assertEquals(webCrawler.start(), webCrawler2.start());
        assertNotEquals(webCrawler3.start(), webCrawler.start());
    }

}
