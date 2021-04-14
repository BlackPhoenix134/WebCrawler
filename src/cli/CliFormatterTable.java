package cli;

import crawler.statistics.DefaultFormatter;
import crawler.statistics.Formatter;
import crawler.statistics.UrlNotFoundFormatter;
import crawler.statistics.VisitedLinksFormatter;
import org.apache.commons.cli.ParseException;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CliFormatterTable {
    public static final String ID_DEFAULT = "default";
    public static final String ID_NOTFOUND = "404";
    public static final String ID_VISITED = "visited";

    private static Map<String, Formatter> formatterMap = new HashMap<>();


    public static List<Formatter> parse(String[] values) throws ParseException {
        List<Formatter> ret = new ArrayList<>();
        for(String value : values) {
            if(!formatterMap.containsKey(value))
                throw new ParseException("Formatter identifier " + value + " not found");
            ret.add(formatterMap.get(value));
        }
        return ret;
    }

    private CliFormatterTable() {
        formatterMap.put(ID_DEFAULT, new DefaultFormatter());
        formatterMap.put(ID_NOTFOUND, new UrlNotFoundFormatter());
        formatterMap.put(ID_VISITED, new VisitedLinksFormatter());
    }

    private static CliFormatterTable initalizer = new CliFormatterTable();
}
