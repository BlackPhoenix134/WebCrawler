package test.crawler.data;

import org.jsoup.nodes.Document;
import crawler.data.PageCrawlResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PageCrawlResultTest {

    private final String URL = "www.example.com";
    private Document TEST_DOC;
    private PageCrawlResult pageCrawlResult;

    @Before
    public void init() {
        TEST_DOC = new Document("./test1.html");
        pageCrawlResult = new PageCrawlResult(URL, TEST_DOC);
    }
    @After
    public void destroy() {
        pageCrawlResult = null;
    }
    @Test
    public void TextCountTest() {
        assertEquals(9, pageCrawlResult.getReadableTextCount());
    }
}
