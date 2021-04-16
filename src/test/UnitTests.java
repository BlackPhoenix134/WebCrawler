
import cli.CommandLineArgsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        crawler.data.CrawlResultTest.class,
        crawler.data.PageCrawlResultTest.class,
        crawler.data.WebCrawlerTest.class,
        CommandLineArgsTest.class

})
public class UnitTests {
}
