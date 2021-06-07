package test.crawler.data;

import crawler.data.CrawlResult;
import crawler.data.WebCrawler;
import crawler.log.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class WebCrawlerTest {
    private final String URL = "https://jsoup.org/cookbook/input/load-document-from-url";
    private final String URL2 = "https://www.aau.at";
    private WebCrawler webCrawler;
    private WebCrawler webCrawler2;
    private WebCrawler webCrawler3;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void init() throws IOException {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        Logger.setLogFunction(System.out::println);
    }
    @After
    public void destroy() {
        webCrawler = null;
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void checkCrawlResult() {
        AtomicReference<CrawlResult> atomicResult1 = new AtomicReference<>(new CrawlResult());
        AtomicReference<CrawlResult> atomicResult2 = new AtomicReference<>(new CrawlResult());
        Thread t1 = new Thread(new WebCrawler(URL, 1, (atomicResult1::set)));
        Thread t2 = new Thread(new WebCrawler(URL, 1, (atomicResult2::set)));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            assertTrue(false);
        }
        CrawlResult result1 = atomicResult1.get();
        CrawlResult result2 = atomicResult2.get();
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
