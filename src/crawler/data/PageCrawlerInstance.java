package crawler.data;

import crawler.log.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PageCrawlerInstance implements Runnable {
    private String starUrl;
    private CrawlResult crawlResult;
    private int depth;

    public PageCrawlerInstance(String startUrl, CrawlResult crawlResult) {
        this(startUrl, crawlResult, 1);
    }

    public PageCrawlerInstance(String startUrl, CrawlResult crawlResult, int depth) {
        this.starUrl = startUrl;
        this.crawlResult = crawlResult;
    }

    @Override
    public void run() {
        crawlRecursive(starUrl, depth, crawlResult);
    }

    private void crawlRecursive(String url, int depth, CrawlResult result) {
        Document doc = null;
        try {
            Logger.information("Accessing: " + url);
            doc = getDocument(url);
            PageCrawlResult pageResult = new PageCrawlResult(url, doc);
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

    private Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
