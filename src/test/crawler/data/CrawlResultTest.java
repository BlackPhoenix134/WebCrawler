package test.crawler.data;

import crawler.data.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CrawlResultTest {
    private WebProcessor webProcessor = new JsoupWebProcessor();

    @Test
    public void testMergeEntry() throws IOException {
        PageCrawlResult aauResult =  webProcessor.getPageCrawlResult("https://www.aau.at");
        PageCrawlResult googleResult =  webProcessor.getPageCrawlResult("https://www.aau.at");
        CrawlResult expectedResult = new CrawlResult();
        CrawlResult expectedResult2 = new CrawlResult();
        expectedResult.merge(aauResult);
        expectedResult2.merge(googleResult);

        expectedResult.merge(googleResult);
        expectedResult2.merge(aauResult);

        compareCrawlResults(expectedResult, expectedResult2);
    }

    private void compareCrawlResults(CrawlResult result1, CrawlResult result2) {
        assertEquals(result1.getNotFoundUrls().size(), result2.getNotFoundUrls().size());
        for(int i = 0; i < result1.getNotFoundUrls().size(); i++) {
            assertTrue(result2.getNotFoundUrls().contains(result1.getNotFoundUrls().toArray()[i]));
            assertTrue(result1.getNotFoundUrls().contains(result2.getNotFoundUrls().toArray()[i]));
        }

        assertEquals(result1.getVisitedPages().size(), result2.getVisitedPages().size());

        for(int i = 0; i < result1.getVisitedPages().size(); i++) {
            assertTrue(result2.getVisitedPages().contains(result1.getVisitedPages().toArray()[i]));
            assertTrue(result1.getVisitedPages().contains(result2.getVisitedPages().toArray()[i]));
        }

        assertEquals(result1.getReadableTextCount(), result2.getReadableTextCount());
    }
}
