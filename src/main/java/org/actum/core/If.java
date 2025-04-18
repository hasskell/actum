package org.actum.core;

import java.util.function.Supplier;

/**
 * If class encapsulates if statements logic
 * Usage:
 * If.when(condition)
 * .then(action)
 * .elseIf(condition, action)
 * .elseThen(action)
 */
public class If {

    private boolean matched;

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
}
