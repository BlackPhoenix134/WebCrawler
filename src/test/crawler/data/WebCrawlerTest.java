package test.crawler.data;

import crawler.data.*;
import crawler.log.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
    private WebProcessor webProcessor = new JsoupWebProcessor();

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
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void checkCrawlResult() throws InterruptedException {
        AtomicReference<CrawlResult> atomicResult1 = new AtomicReference<>(new CrawlResult());
        AtomicReference<CrawlResult> atomicResult2 = new AtomicReference<>(new CrawlResult());
        Thread t1 = new Thread(new WebCrawler(URL, webProcessor, 1, (atomicResult1::set)));
        Thread t2 = new Thread(new WebCrawler(URL, webProcessor, 1, (atomicResult2::set)));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        CrawlResult result1 = atomicResult1.get();
        CrawlResult result2 = atomicResult2.get();
        compareCrawlResults(result1, result2);
    }


    @Test
    public void criticalSectionMerge() throws IOException, InterruptedException {
        Document TEST_DOC1 = Jsoup.connect("https://www.aau.at").get();
        Document TEST_DOC2 = Jsoup.connect("https://www.google.at").get();

        PageCrawlResult pageResult1 = webProcessor.getPageCrawlResult("https://www.aau.at");
        PageCrawlResult pageResult2 = webProcessor.getPageCrawlResult("https://www.google.at");

        CrawlResult syncronizedResult = new CrawlResult();
        CrawlResult concurrentResult = new CrawlResult();

        syncronizedResult.merge(pageResult1);
        syncronizedResult.merge(pageResult2);

        Thread t1 = new Thread(() -> {
            concurrentResult.merge(pageResult1);
        });

        Thread t2 = new Thread(() -> {
            concurrentResult.merge(pageResult2);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        compareCrawlResults(syncronizedResult, concurrentResult);
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
