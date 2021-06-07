package crawler.data;

import crawler.log.Logger;
import org.jsoup.HttpStatusException;
import java.io.IOException;

public class PageCrawlerInstance implements Runnable {
    private int id;
    private String starUrl;
    private CrawlResult crawlResult;
    private WebProcessor webProcessor;
    private int depth;

    public PageCrawlerInstance(int id, String startUrl, CrawlResult crawlResult, WebProcessor webProcessor) {
        this(id, startUrl, crawlResult, webProcessor, 1);
    }

    public PageCrawlerInstance(int id, String startUrl, CrawlResult crawlResult,  WebProcessor webProcessor, int depth) {
        this.id = id;
        this.starUrl = startUrl;
        this.crawlResult = crawlResult;
        this.webProcessor = webProcessor;
        this.depth = depth;
    }

    @Override
    public void run() {
        crawlRecursive(starUrl, depth, crawlResult);
    }

    private void crawlRecursive(String url, int depth, CrawlResult result) {
        try {
            Logger.information("Thread " + id +" Accessing: " + url);
            PageCrawlResult pageResult = webProcessor.getPageCrawlResult(url);
            result.merge(pageResult);

            if(depth >= 0) {
                for(String link : pageResult.getAbsoluteLinks()) {
                    if(!result.wasVisited(link))
                        crawlRecursive(link, depth-1, result);
                }
            }
        } catch (HttpStatusException e) {
            result.addNotFoundUrl(url);
        } catch (IOException e) {
            Logger.error("Error connecting to url " + e.getMessage());
        }
    }

}
