package crawler.data;

import java.io.IOException;

public interface WebProcessor {
    PageCrawlResult getPageCrawlResult(String url) throws IOException;
}
