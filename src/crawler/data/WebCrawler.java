package crawler.data;
import jdk.jshell.spi.ExecutionControl;
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
            System.out.println("Crawling: " + url);
            doc = getDocument(url);
            PageCrawlResult pageResult = new PageCrawlResult(url, doc);
            result.merge(pageResult);

            if(depth >= 0) {
                for(String link : pageResult.getAbsoluteLinks()) {
                    if(!result.wasVisited(link))
                        crawlRecursive(link, depth-1, result);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
