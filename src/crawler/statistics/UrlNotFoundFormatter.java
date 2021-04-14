package crawler.statistics;

import crawler.data.CrawlResult;
import crawler.misc.Constants;

public class UrlNotFoundFormatter implements Formatter  {
    @Override
    public String format(CrawlResult result) {
        String nl = Constants.NEW_LINE;
        StringBuilder builder = new StringBuilder();
        builder.append("404 Urls").append(nl);
        for(String url : result.getNotFoundUrls()) {
            builder.append(url).append(nl);
        }
        return builder.toString();
    }
}
