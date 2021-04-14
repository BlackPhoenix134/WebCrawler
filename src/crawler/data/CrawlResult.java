package crawler.data;

import org.jsoup.nodes.Element;

import java.util.*;

public class CrawlResult {
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
}
