package crawler.data;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageCrawlResult {
    private String url;
    private long readableTextCount;
    private Map<String, List<Element>> content = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public long getReadableTextCount() {
        return readableTextCount;
    }

    public Map<String, List<Element>> getContent() {
        return content;
    }

    public PageCrawlResult(String url) {
        this.url = url;
    }

    void setReadableTextCount(long readableTextCount) {
        this.readableTextCount = readableTextCount;
    }

    public List<String> getAbsoluteLinks() {
        List<String> urls = new ArrayList<>();
        for(Element linkTag : content.get("a")) {
            String url = linkTag.attr("abs:href");
            if(!url.contains("#") && !url.isEmpty()) {
                urls.add(url);
            }
        }
        return urls;
    }

    void addContent(Element element) {
        String key = element.nodeName();
        if(!content.containsKey(key))
            content.put(key, new ArrayList<>());
       content.get(key).add(element);
    }

}
