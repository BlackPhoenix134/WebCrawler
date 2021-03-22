package crawler.log;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Logger {
    private static Consumer<String> logFunction;

    public static void setLogFunction(Consumer<String> logF) {
        logFunction = logF;
    }

    public static void information(String message) {
        logFunction.accept("Info:\t" + message);
    }

    public static void debug(String message) {
        logFunction.accept("Debug:\t" + message);
    }

    public static void error(String message) {
        logFunction.accept("Error:\t" + message);
    }
}
