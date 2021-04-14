package crawler.log;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Logger {
    private static int LogLevel = Integer.MAX_VALUE;
    private static Consumer<String> logFunction;

    public static int getLogLevel() {
        return LogLevel;
    }

    public static void setLogLevel(int logLevel) {
        LogLevel = logLevel;
    }

    public static Consumer<String> getLogFunction() {
        return logFunction;
    }

    public static void setLogFunction(Consumer<String> logF) {
        logFunction = logF;
    }

    public static void information(String message) {
        if(LogLevel > 2)
            logFunction.accept("Info:\t" + message);
    }

    public static void debug(String message) {
        if(LogLevel > 1)
            logFunction.accept("Debug:\t" + message);
    }

    public static void error(String message) {
        if(LogLevel > 0)
            logFunction.accept("Error:\t" + message);
    }

    public static void out(String message) {
        logFunction.accept(message);
    }
}
