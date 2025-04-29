package org.actum.conditional.value;

import org.actum.logger.ActumLogger;
import org.actum.logger.LoggerSupport;
import org.actum.visibility.Debuggable;
import org.actum.visibility.Describable;
import org.actum.visibility.Traceable;
import org.actum.visibility.Viewable;

import java.util.function.Consumer;

public class IfValue<T> implements Debuggable, Traceable<IfValue<T>>, Viewable<IfValue<T>>, Describable<IfValue<T>>, LoggerSupport<IfValue<T>> {

    private boolean matched;
    private String label;
    private String description;
    private boolean traceable = false;
    private ActumLogger logger = ((logLevel, message) -> {
    });

    @Override
    public IfValue<T> withLogger(ActumLogger logger) {
        this.logger = logger;
        return this;
    }

    /**
     * Returns debuggable string
     *
     * @return debuggable string
     */
    @Override
    public String debug() {
        return String.format("If[Label=%s, Description=%s, Traceable=%s, Matched=%s]", label, description, traceable, matched);
    }

    /**
     * Labels current condition
     *
     * @param label label
     * @return label
     */
    @Override
    public IfValue<T> label(String label) {
        this.label = label == null ? "" : label;
        return this;
    }

    /**
     * Adds description to condition
     *
     * @param describe description message
     * @return description
     */
    @Override
    public IfValue<T> describe(String describe) {
        this.description = describe == null ? "No description provided!" : describe;
        return this;
    }

    /**
     * Trace current condition
     *
     * @return trace
     */
    @Override
    public IfValue<T> trace() {
        this.traceable = true;
        return this;
    }

    /**
     * Used for side effects or debugging
     *
     * @param action action
     * @return action result
     */
    @Override
    public IfValue<T> peek(Runnable action) {
        if (this.matched) {
            action.run();
        }
        return this;
    }

    /**
     * Used for side effects or debugging
     *
     * @param consumer consumer
     * @return consumer result
     */
    @Override
    public IfValue<T> view(Consumer<IfValue<T>> consumer) {
        consumer.accept(this);
        return this;
    }
}
