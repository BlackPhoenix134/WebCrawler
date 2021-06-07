package cli;

import org.apache.commons.cli.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CommandLineArgsTest {

    private CommandLineArgs commandLineArgs;
    @Before
    public void init() {
        commandLineArgs = new CommandLineArgs();
    }
    @After
    public void destroy() {
        commandLineArgs = null;
    }
    @Test
    public void checkDefaultArgs() throws ParseException {

        assertEquals(0, commandLineArgs.getStartUrls().size());
        assertEquals(2, commandLineArgs.getDepth());
        assertNull(commandLineArgs.getOutFilePath());
        assertEquals(3, commandLineArgs.getLogLevel());
    }
    @Test
    public void checkShortArgs() throws ParseException {
        String testUrl = "https://www.aau.at";
        List<String> urlList = new ArrayList<>();
        urlList.add(testUrl);
        int depth = 2;
        String filePath = "/test/example";
        String[] args = {"-u", testUrl, "-d", depth+"", "-o", filePath, "-ll", 2+""};
        commandLineArgs.parse(args);
        assertEquals(urlList, commandLineArgs.getStartUrls());
        assertEquals(depth, commandLineArgs.getDepth());
        assertEquals(filePath, commandLineArgs.getOutFilePath());
        assertEquals(2, commandLineArgs.getLogLevel());
    }
    @Test
    public void checkLongArgs() throws ParseException {
        String testUrl = "https://www.aau.at";
        int depth = 2;
        List<String> urlList = new ArrayList<>();
        urlList.add(testUrl);
        String filePath = "/test/example";
        String[] args = {"-url", testUrl, "-depth", depth+"", "-outfile", filePath, "-loglevel", 2+""};
        commandLineArgs.parse(args);
        assertEquals(urlList, commandLineArgs.getStartUrls());
        assertEquals(depth, commandLineArgs.getDepth());
        assertEquals(filePath, commandLineArgs.getOutFilePath());
        assertEquals(2, commandLineArgs.getLogLevel());
    }
}
