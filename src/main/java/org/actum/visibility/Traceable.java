package org.actum.visibility;

import org.actum.logger.ActumLogger;
import org.actum.logger.LogLevel;

import java.util.function.Supplier;

/**
 * Provide capabilities to trace current condition
 * @param <T> type of trace
 */
public interface Traceable<T> {
    /**
     * Trace current condition
     * @return trace
     */
    T trace();

    /**
     * Provides default logging
     * @param logger Logger
     * @param level Log Level
     * @param message Message to be logged
     * @param traceable Boolean flag if trace is enabled
     */
    default void log(ActumLogger logger, LogLevel level, Supplier<String> message, boolean traceable) {
        if (traceable) {
            logger.log(level, message.get());
        }
    }
}
