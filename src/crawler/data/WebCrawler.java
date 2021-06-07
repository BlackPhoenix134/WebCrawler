package crawler.data;
import crawler.log.Logger;
import jdk.jshell.spi.ExecutionControl;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class WebCrawler implements Runnable {
    private List<String> urls;
    private int depth;
    private Consumer<CrawlResult> onFinished;

    public WebCrawler(String url, int depth, Consumer<CrawlResult> onFinished) {
        this(Collections.singletonList(url), depth, onFinished);
    }

    public WebCrawler(List<String> urls, int depth, Consumer<CrawlResult> onFinished) {
        this.urls = urls;
        this.depth = depth;
        this.onFinished = onFinished;
    }

    @Override
    public void run() {
        CrawlResult crawlResult = new CrawlResult();
        List<Thread> threads = new ArrayList<>();

        for(int i = 0; i < urls.size(); i++) {
            Thread thread = new Thread(new PageCrawlerInstance(i, urls.get(0), crawlResult, depth));
            threads.add(thread);
            thread.start();
        }
        Logger.information("Awaiting " + urls.size() + " threads");
        awaitAll(threads);
        onFinished.accept(crawlResult);
    }

    private void awaitAll( List<Thread> threads) {
        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignore) {
                ignore.printStackTrace();
            }
        }
    }
}
