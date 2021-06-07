package crawler.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupWebProcessor implements WebProcessor {
    @Override
    public PageCrawlResult getPageCrawlResult(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        return parse(url, document);
    }

    private PageCrawlResult parse(String url, Document doc) {
        PageCrawlResult crawlResult = new PageCrawlResult(url);
        Elements elements = doc.getAllElements();
        crawlResult.setReadableTextCount(crawlResult.getReadableTextCount() + getTextCount(doc));
        for(Element element  : elements) {
            crawlResult.addContent(element);
        }
        return crawlResult;
    }

    private long getTextCount(Document document) {
        return document.text().split(" ").length;
    }
}
