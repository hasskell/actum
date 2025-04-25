package org.actum.logger;

@FunctionalInterface
public interface ActumLogger {
    void log(LogLevel logLevel, String message);
}
