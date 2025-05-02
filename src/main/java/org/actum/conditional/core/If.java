package org.actum.conditional.core;

import org.actum.logger.ActumLogger;
import org.actum.logger.LogLevel;
import org.actum.logger.LoggerSupport;
import org.actum.util.Formatter;
import org.actum.visibility.Debuggable;
import org.actum.visibility.Describable;
import org.actum.visibility.Traceable;
import org.actum.visibility.Viewable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.actum.util.Validator.checkNotNull;

/**
 * If class encapsulates if statements logic
 * Usage:
 * If.when(condition)
 * .then(action)
 * .elseIf(condition, action)
 * .elseThen(action)
 */
public class If implements Debuggable,
        Describable<If>,
        Traceable<If>,
        Viewable<If>,
        LoggerSupport<If> {

    private static final ActumLogger NO_OP_LOGGER = (logLevel, message) -> {};
    private boolean matched;
    private String label;
    private String description;
    private boolean traceable = false;
    private ActumLogger logger = NO_OP_LOGGER;

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
     *
     */
    public If then(Runnable action) {
        checkNotNull(action);
        if (this.matched) {
            log(this.logger, LogLevel.DEBUG, () -> String.format("[ %s ] Executing Then block (matched=%s), (description=%s)",
                    Formatter.normalize(this.getLabel(), this.getClass().getSimpleName()), this.matched, this.getDescription()), this.traceable);
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
        checkNotNull(action);
        if (!this.matched && condition) {
            this.matched = true;
            log(this.logger, LogLevel.DEBUG, () -> String.format("[ %s ] Executing ElseIf block (matched=%s), (condition=%s), (description=%s)",
                    Formatter.normalize(this.getLabel(), this.getClass().getSimpleName()), this.matched, condition, this.getDescription()), this.traceable);
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
        checkNotNull(action);
        if (!this.matched) {
            log(this.logger, LogLevel.DEBUG, () -> String.format("[ %s ] Executing ElseThen block (matched=%s), (description=%s)",
                    Formatter.normalize(this.getLabel(), this.getClass().getSimpleName()), this.matched, this.getDescription()), this.traceable);
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
            log(this.logger, LogLevel.DEBUG, () -> String.format("[ %s ] Throwing Exception (exception=%s), (matched=%s), (description=%s)",
                    Formatter.normalize(this.getLabel(), this.getClass().getSimpleName()), exception.getClass().getSimpleName(), this.matched, this.getDescription()), this.traceable);
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
        this.label = label == null || label.isBlank() ? getClass().getSimpleName() : label;
        return this;
    }

    /**
     * Adds description to condition
     *
     * @param description description message
     * @return description
     */
    @Override
    public If describe(String description) {
        this.description = description == null ? "No description provided!" : description;
        return this;
    }


    @Override
    public If withLogger(ActumLogger logger) {
        this.logger = logger;
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
    public If peek(Runnable action) {
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
    public If view(Consumer<If> consumer) {
        consumer.accept(this);
        return this;
    }

    public String getLabel() {
        return label == null || label.isBlank() ? getClass().getSimpleName() : label;
    }

    public String getDescription() {
        return description == null ? "No description provided!" : description;
    }

    public boolean isMatched() {
        return matched;
    }

    @Override
    public String toString() {
        return this.debug();
    }
}
