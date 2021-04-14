package crawler.data;
import crawler.log.Logger;
import jdk.jshell.spi.ExecutionControl;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URI;

//ToDo: probably  async for larger crawls
//ToDo: if async, stop/continue functionality
public class WebCrawler {
    private String starUrl;

    public WebCrawler(String startUrl) {
        this.starUrl = startUrl;
    }

    public CrawlResult start() {
        return start(1);
    }

    public CrawlResult start(int depth) {
        CrawlResult result = new CrawlResult();
        crawlRecursive(starUrl, depth, result);
        return result;
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

    private Document getDocument(String url) throws IOException, HttpStatusException {
        return Jsoup.connect(url).get();
    }
}
