package crawler.data;

import crawler.misc.Constants;
import crawler.statistics.Formatter;
import org.jsoup.nodes.Element;

import java.util.*;


public class CrawlResult {
    private List<Formatter> dataFormatter = new ArrayList<>();
    private long readableTextCount;
    private final Map<String, List<Element>> content = new HashMap<>();
    private final Set<String> notFoundUrls = new HashSet<>();
    private final Set<String> visitedPages = new HashSet<>();

    public long getReadableTextCount() {
        return readableTextCount;
    }

    public Map<String, List<Element>> getContent() {
        return content;
    }

    public Set<String> getNotFoundUrls() {
        return notFoundUrls;
    }

    public Set<String> getVisitedPages() {
        return visitedPages;
    }

    public boolean wasVisited(String url) {
        return visitedPages.contains(url);
    }

    public void addNotFoundUrl(String url) {
        notFoundUrls.add(url);
    }

    public void merge(PageCrawlResult pageResult) {
        readableTextCount += pageResult.getReadableTextCount();
        visitedPages.add(pageResult.getUrl());
        for(Map.Entry<String, List<Element>> entry : pageResult.getContent().entrySet()) {
            mergeEntry(entry);
        }
    }

    private void mergeEntry(Map.Entry<String, List<Element>> entry) {
        String key = entry.getKey();
        if(!content.containsKey(key))
            content.put(key, new ArrayList<>());
        content.get(key).addAll(entry.getValue());
    }

    public void addDataFormatter(Collection<Formatter> formatters) {
        dataFormatter.addAll(formatters);
    }

    public void addDataFormatter(Formatter formatter) {
        dataFormatter.add(formatter);
    }

    public String getFormattedData() {
        StringBuilder builder = new StringBuilder();
        for(Formatter formatter : dataFormatter) {
            builder.append("Format: ").append(formatter.getClass().getSimpleName()).append(Constants.NEW_LINE)
                .append(formatter.format(this)).append(Constants.NEW_LINE);
        }
        return builder.toString();
    }
}
