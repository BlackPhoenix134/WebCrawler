package test.crawler.data;

import crawler.data.CrawlResult;
import crawler.data.PageCrawlResult;
import crawler.data.WebCrawler;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WebCrawlerTest {

    private final String URL = "https://jsoup.org/cookbook/input/load-document-from-url";
    private Document TEST_DOC;
    private WebCrawler webCrawler;
    private Map<String, List<Element>> exampleMap = new HashMap<>();


    @Before
    public void init() throws IOException {
        TEST_DOC = Jsoup.connect(URL).get();
        webCrawler = new WebCrawler(URL);
    }
    @After
    public void destroy() {
        webCrawler = null;
    }
    @Test
    public void checkCrawlResult() {
        CrawlResult expectedResult = new CrawlResult();
        PageCrawlResult pageCrawlResult = new PageCrawlResult(URL, TEST_DOC);
        expectedResult.merge(pageCrawlResult);
        CrawlResult result = webCrawler.start();
        assertNotNull(result);
       // assertEquals(expectedResult, webCrawler.start());
    }

}
