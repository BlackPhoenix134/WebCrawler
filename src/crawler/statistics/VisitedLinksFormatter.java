package crawler.statistics;

import crawler.data.CrawlResult;
import crawler.misc.Constants;

public class VisitedLinksFormatter implements Formatter {
    @Override
    public String format(CrawlResult result) {
        String nl = Constants.NEW_LINE;
        StringBuilder builder = new StringBuilder();
        builder.append("Visited Urls").append(nl);
        for(String url : result.getVisitedPages()) {
            builder.append(url).append(nl);
        }
        return builder.toString();
    }
}
