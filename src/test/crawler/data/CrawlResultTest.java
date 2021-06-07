package test.crawler.data;

import crawler.data.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CrawlResultTest {
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
