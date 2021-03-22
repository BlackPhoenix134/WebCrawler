package crawler.data;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageCrawlResult {
    private int readableTextCount;
    private Map<String, List<Element>> content = new HashMap<>();

    public int getReadableTextCount() {
        return readableTextCount;
    }

    public Map<String, List<Element>> getContent() {
        return content;
    }

    public PageCrawlResult(Document doc) {
        parse(doc);
    }

    private void parse(Document doc) {
        Elements elements = doc.getAllElements();
        for(Element element  : elements) {
            readableTextCount += element.text().length();
            addContent(element);
        }
    }

    private void addContent(Element element) {
        String key = element.nodeName();
        if(!content.containsKey(key))
            content.put(key, new ArrayList<>());
       content.get(key).add(element);
    }

}
