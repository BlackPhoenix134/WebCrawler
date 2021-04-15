package test.crawler.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import crawler.data.PageCrawlResult;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PageCrawlResultTest {

    private final String URL = "https://jsoup.org/cookbook/input/load-document-from-url";
    private Document TEST_DOC;
    private PageCrawlResult pageCrawlResult;
    private Map<String, List<Element>> exampleMap = new HashMap<>();


    @Before
    public void init() throws IOException {
        TEST_DOC = Jsoup.connect(URL).get();
        pageCrawlResult = new PageCrawlResult(URL, TEST_DOC);
    }
    @After
    public void destroy() {
        pageCrawlResult = null;
    }
    @Test
    public void textCountTest() {
        assertEquals(253, pageCrawlResult.getReadableTextCount());
    }
    @Test
    public void getAbsoluteLinksTest() {
        assertEquals(28, pageCrawlResult.getAbsoluteLinks().size());
    }
    @Test
    public void getUrlTest() {
        assertEquals(URL, pageCrawlResult.getUrl());
    }
    @Test
    public void getContentTest() {
        assertEquals("[<h1>Load a Document from a URL</h1>]", pageCrawlResult.getContent().get("h1").toString());
        assertEquals(22, pageCrawlResult.getContent().size());
    }
}
