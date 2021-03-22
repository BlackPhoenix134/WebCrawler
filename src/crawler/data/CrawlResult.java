package crawler.data;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrawlResult {
    private int readableTextCount;
    private Map<String, List<Element>> content = new HashMap<>();

    public void merge(PageCrawlResult pageResult) {
        readableTextCount += pageResult.getReadableTextCount();
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
