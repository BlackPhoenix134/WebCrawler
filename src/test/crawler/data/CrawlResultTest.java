package crawler.data;

import crawler.data.CrawlResult;
import crawler.data.PageCrawlResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CrawlResultTest {

    private CrawlResult crawlResult;
    @Before
    public void init() {
        crawlResult = new CrawlResult();
    }
    @After
    public void destroy() {
        crawlResult = null;
    }

    @Test
    public void testMergeEntry() throws IOException {
        Document TEST_DOC1 = Jsoup.connect("https://www.aau.at").get();
        Document TEST_DOC2 = Jsoup.connect("https://www.google.at").get();
        PageCrawlResult aauResult = new PageCrawlResult("https://www.aau.at", TEST_DOC1);
        PageCrawlResult googleResult = new PageCrawlResult("https://www.aau.at", TEST_DOC2);
        CrawlResult expectedResult = new CrawlResult();
        CrawlResult expectedResult2 = new CrawlResult();
        expectedResult.merge(aauResult);
        expectedResult2.merge(googleResult);

        assertNotEquals(expectedResult, expectedResult2);

        expectedResult.merge(googleResult);
        expectedResult2.merge(aauResult);

        assertEquals(expectedResult, expectedResult2);
    }
}
