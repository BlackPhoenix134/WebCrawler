package test.crawler.data;

import crawler.data.CrawlResult;
import org.junit.After;
import org.junit.Before;

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
}
