package org.actum.conditional.value;

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

public class IfValue<T> implements Debuggable,
        Traceable<IfValue<T>>,
        Viewable<IfValue<T>>,
        Describable<IfValue<T>>,
        LoggerSupport<IfValue<T>> {

    private boolean matched;
    private String label;
    private String description;
    private boolean traceable = false;
    private T result;
    private ActumLogger logger = ((logLevel, message) -> {
    });

    private IfValue() {
    }

    /**
     * Accepts condition on what act upon
     *
     * @param condition logical condition
     * @return instance of type T
     */
    public static <T> IfValue<T> when(boolean condition) {
        IfValue<T> instance = new IfValue<>();
        if (condition) {
            instance.matched = true;
        }
        return instance;
    }

    /**
     * Acts based on action
     *
     * @param action action
     * @return instance of type T
     */
    public IfValue<T> then(Supplier<T> action) {
        checkNotNull(action);
        if (this.matched) {
            log(this.logger, LogLevel.DEBUG, () -> String.format("[ %s ] Executing Then block (matched=%s), (description=%s)",
                    Formatter.normalize(this.label, this.getClass().getSimpleName()), this.matched, this.description), this.traceable);
            result = action.get();
        }
        return this;
    }

    /**
     * Accepts condition and action
     *
     * @param condition logical condition
     * @param action    action
     * @return instance of type T
     */
    public IfValue<T> elseIf(boolean condition, Supplier<T> action) {
        checkNotNull(action);
        if (!this.matched && condition) {
            this.matched = true;
            log(this.logger, LogLevel.DEBUG, () -> String.format("[ %s ] Executing ElseIf block (matched=%s), (condition=%s), (description=%s)",
                    Formatter.normalize(this.label, this.getClass().getSimpleName()), this.matched, condition, this.description), this.traceable);
            result = action.get();
        }
        return this;
    }

    /**
     * Acts based on action
     *
     * @param action action
     * @return result of type T
     */
    public T elseThen(Supplier<T> action) {
        checkNotNull(action);
        if (!this.matched) {
            log(this.logger, LogLevel.DEBUG, () -> String.format("[ %s ] Executing ElseThen block (matched=%s), (description=%s)",
                    Formatter.normalize(this.label, this.getClass().getSimpleName()), this.matched, this.description), this.traceable);
            result = action.get();
        }
        return result;
    }

    /**
     * Throws exception
     *
     * @param exception exception to throw
     */
    public void orThrows(Supplier<? extends RuntimeException> exception) {
        if (!this.matched) {
            log(this.logger, LogLevel.DEBUG, () -> String.format("[ %s ] Throwing Exception (exception=%s), (matched=%s), (description=%s)",
                    Formatter.normalize(this.label, this.getClass().getSimpleName()), exception.getClass().getSimpleName(), this.matched, this.description), this.traceable);
            throw exception.get();
        }
    }

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
