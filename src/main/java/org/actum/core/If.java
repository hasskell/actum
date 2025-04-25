package org.actum.core;

import org.actum.visibility.Debuggable;
import org.actum.visibility.Describable;
import org.actum.visibility.Traceable;
import org.actum.visibility.Viewable;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * If class encapsulates if statements logic
 * Usage:
 * If.when(condition)
 * .then(action)
 * .elseIf(condition, action)
 * .elseThen(action)
 */
public class If implements Debuggable, Describable<If>, Traceable<If>, Viewable<If> {

    private boolean matched;
    private String label;
    private String description;
    private boolean traceable = false;

    /**
     * Accepts condition on what act upon
     *
     * @param condition logical condition
     * @return instance
     */
    public static If when(boolean condition) {
        If instance = new If();
        if (condition) {
            instance.matched = true;
        }
        return instance;
    }

    /**
     * Acts based on action
     *
     * @param action action
     * @return instance
     */
    public If then(Runnable action) {
        if (this.matched) {
            action.run();
        }
        return this;
    }

    /**
     * Accepts condition and action
     *
     * @param condition logical condition
     * @param action    action
     * @return instance
     */
    public If elseIf(boolean condition, Runnable action) {
        if (!this.matched && condition) {
            this.matched = true;
            action.run();
        }
        return this;
    }

    /**
     * Acts based on action
     *
     * @param action action
     */
    public void elseThen(Runnable action) {
        if (!this.matched) {
            action.run();
        }
    }

    /**
     * Throws exception
     *
     * @param exception exception to throw
     */
    public void orThrows(Supplier<? extends RuntimeException> exception) {
        if (!this.matched) {
            throw exception.get();
        }
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
    public If label(String label) {
        this.label = label;
        return this;
    }

    /**
     * Adds description to condition
     *
     * @param describe description message
     * @return description
     */
    @Override
    public If describe(String describe) {
        this.description = describe;
        return this;
    }

    /**
     * Trace current condition
     *
     * @return trace
     */
    @Override
    public If trace() {
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
    public If peak(Runnable action) {
        if (this.matched){
            action.run();
        }
        return null;
    }

    /**
     * Used for side effects or debugging
     *
     * @param consumer consumer
     * @return consumer result
     */
    @Override
    public If view(Consumer<If> consumer) {
        consumer.accept(this);
        return this;
    }
}
