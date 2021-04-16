package crawler.data;

import crawler.log.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

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
        // needs to be set, otherwise it throws an exception.
        Logger.setLogFunction(System.out::println);

        webCrawler = new WebCrawler(URL);
        webCrawler2 = new WebCrawler(URL);
        webCrawler3 = new WebCrawler(URL2);
    }
    @After
    public void destroy() {
        webCrawler = null;
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    @Test
    public void checkCrawlResult() {
        assertEquals(webCrawler.start(), webCrawler2.start());
        assertNotEquals(webCrawler3.start(), webCrawler.start());
    }
    @Test
    public void malformedUrlTest() {
        webCrawler = new WebCrawler("noURL");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            webCrawler.start();
        });
    }

    @Test
    public void checkCrawlerOutputError() {
        webCrawler = new WebCrawler("https://www.noUrl.at");
        webCrawler.start();
        assertEquals("Info:\tAccessing: https://www.noUrl.at\nError:\tError connecting to url www.noUrl.at\n", outContent.toString());

    }
}
