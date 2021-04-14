package crawler.statistics;

import crawler.data.CrawlResult;
import crawler.log.Logger;
import crawler.misc.Constants;

public class DefaultFormatter implements Formatter {
    @Override
    public String format(CrawlResult result) {
        String nl = Constants.NEW_LINE;
        StringBuilder builder = new StringBuilder();
        builder.append("Words: ").append(result.getReadableTextCount()).append(nl);
        builder.append("404: ").append(result.getNotFoundUrls().size()).append(nl);
        builder.append("Visited Pages: ").append(result.getVisitedPages().size()).append(nl);
        return builder.toString();
    }
}
