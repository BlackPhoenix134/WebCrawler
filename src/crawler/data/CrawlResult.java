package crawler.data;

import org.jsoup.nodes.Element;

import java.util.*;

public class CrawlResult {
    private long readableTextCount;
    private Map<String, List<Element>> content = new HashMap<>();
    private Set<String> visitedPages = new HashSet<>();

    public boolean wasVisited(String url) {
        return visitedPages.contains(url);
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
