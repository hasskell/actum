package org.actum.conditional.value;

import org.actum.logger.ActumLogger;
import org.actum.logger.LoggerSupport;
import org.actum.visibility.Debuggable;
import org.actum.visibility.Describable;
import org.actum.visibility.Traceable;
import org.actum.visibility.Viewable;

import java.util.function.Consumer;

public class SwitchValue<T, V> implements Debuggable,
        Traceable<SwitchValue<T, V>>,
        Viewable<SwitchValue<T, V>>,
        Describable<SwitchValue<T, V>>,
        LoggerSupport<SwitchValue<T, V>> {

    private boolean matched;
    private String label;
    private String description;
    private boolean traceable = false;
    private ActumLogger logger = ((logLevel, message) -> {
    });

    @Override
    public SwitchValue<T, V> withLogger(ActumLogger logger) {
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
    public SwitchValue<T, V> label(String label) {
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
    public SwitchValue<T, V> describe(String describe) {
        this.description = describe == null ? "No description provided!" : describe;
        return this;
    }

    /**
     * Trace current condition
     *
     * @return trace
     */
    @Override
    public SwitchValue<T, V> trace() {
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
    public SwitchValue<T, V> peek(Runnable action) {
        if (this.matched){
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
    public SwitchValue<T, V> view(Consumer<SwitchValue<T, V>> consumer) {
        consumer.accept(this);
        return this;
    }
}
