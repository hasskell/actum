package org.actum.visibility;

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
}
