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
    private int id;

    public PageCrawlerInstance(int id, String startUrl, CrawlResult crawlResult) {
        this(id, startUrl, crawlResult, 1);
    }

    public PageCrawlerInstance(int id, String startUrl, CrawlResult crawlResult, int depth) {
        this.id = id;
        this.starUrl = startUrl;
        this.crawlResult = crawlResult;
        this.depth = depth;
    }

    @Override
    public void run() {
        crawlRecursive(starUrl, depth, crawlResult);
    }

    private void crawlRecursive(String url, int depth, CrawlResult result) {
        Document doc = null;
        try {
            Logger.information("Thread " + id +" Accessing: " + url);
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
