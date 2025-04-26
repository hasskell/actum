package org.actum.conditional.value;

import org.actum.logger.ActumLogger;
import org.actum.logger.LoggerSupport;
import org.actum.visibility.Debuggable;
import org.actum.visibility.Describable;
import org.actum.visibility.Traceable;
import org.actum.visibility.Viewable;

import java.util.function.Consumer;

public class SwitchValue<T, V> implements Debuggable, Traceable<T>, Viewable<T>, Describable<T>, LoggerSupport<T> {

    private boolean matched;
    private String label;
    private String description;
    private boolean traceable = false;
    private ActumLogger logger = ((logLevel, message) -> {});

    @Override
    public T withLogger(ActumLogger logger) {
        return null;
    }

    /**
     * Returns debuggable string
     *
     * @return debuggable string
     */
    @Override
    public String debug() {
        return "";
    }

    /**
     * Labels current condition
     *
     * @param label label
     * @return label
     */
    @Override
    public T label(String label) {
        return null;
    }

    /**
     * Adds description to condition
     *
     * @param describe description message
     * @return description
     */
    @Override
    public T describe(String describe) {
        return null;
    }

    /**
     * Trace current condition
     *
     * @return trace
     */
    @Override
    public T trace() {
        return null;
    }

    /**
     * Used for side effects or debugging
     *
     * @param action action
     * @return action result
     */
    @Override
    public T peek(Runnable action) {
        return null;
    }

    /**
     * Used for side effects or debugging
     *
     * @param consumer consumer
     * @return consumer result
     */
    @Override
    public T view(Consumer<T> consumer) {
        return null;
    }
}
