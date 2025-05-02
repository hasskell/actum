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

public class SwitchValue<I, R> implements Debuggable,
        Traceable<SwitchValue<I, R>>,
        Viewable<SwitchValue<I, R>>,
        Describable<SwitchValue<I, R>>,
        LoggerSupport<SwitchValue<I, R>> {

    private boolean matched;
    private String label;
    private String description;
    private boolean traceable = false;
    private I input;
    private R result;
    private ActumLogger logger = ((logLevel, message) -> {
    });

    private SwitchValue(I input){
        this.input = input;
    }

    /**
     * Accepts Object as input for comparison
     * @param input input object
     * @return instance of type I, R
     */
    public static <I, R> SwitchValue<I, R> on(I input){
        return new SwitchValue<>(input);
    }

    /**
     * Matches case object with input and executes action
     * @param match object to match
     * @param action action to execute
     * @return instance of type R
     */
    public SwitchValue<I, R> caseOf(I match, Supplier<R> action){
        checkNotNull(action);
        if (input.equals(match)){
            this.matched = true;
            log(this.logger, LogLevel.DEBUG, () -> String.format("[ %s ] Executing CaseOf block (matched=%s), (description=%s)",
                    Formatter.normalize(this.label, this.getClass().getSimpleName()), this.matched, this.description), this.traceable);
            result = action.get();
        }
        return this;
    }

    /**
     * Executes default action if no of the cases are true
     * @param action action to execute
     * @return instance of type R
     */
    public SwitchValue<I, R> defaultOf(Supplier<R> action){
        checkNotNull(action);
        this.matched = false;
        log(this.logger, LogLevel.DEBUG, () -> String.format("[ %s ] Executing DefaultOf block (matched=%s), (description=%s)",
                Formatter.normalize(this.label, this.getClass().getSimpleName()), this.matched, this.description), this.traceable);
        result = action.get();
        return this;
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
    public SwitchValue<I, R> withLogger(ActumLogger logger) {
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
    public SwitchValue<I, R> label(String label) {
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
    public SwitchValue<I, R> describe(String describe) {
        this.description = describe == null ? "No description provided!" : describe;
        return this;
    }

    /**
     * Trace current condition
     *
     * @return trace
     */
    @Override
    public SwitchValue<I, R> trace() {
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
    public SwitchValue<I, R> peek(Runnable action) {
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
    public SwitchValue<I, R> view(Consumer<SwitchValue<I, R>> consumer) {
        consumer.accept(this);
        return this;
    }
}
