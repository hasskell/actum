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

public class Switch implements Debuggable,
        LoggerSupport<Switch>,
        Traceable<Switch>,
        Describable<Switch>,
        Viewable<Switch> {

    private boolean matched;
    private String label;
    private String description;
    private boolean traceable = false;
    private ActumLogger logger = ((logLevel, message) -> {});
    private final Object input;

    private Switch(Object input){
        this.input = input;
    }

    /**
     * Accepts Object as input for comparison
     * @param input input object
     * @return instance
     */
    public static Switch on(Object input){
        return new Switch(input);
    }

    /**
     * Matches case object with input and executes action
     * @param match object to match
     * @param action action to execute
     * @return instance
     */
    public Switch caseOf(Object match, Runnable action){
        if (input.equals(match)){
            this.matched = true;
            log(this.logger, LogLevel.DEBUG, String.format("[ %s ] Executing CaseOf block (matched=%s), (description=%s)",
                    Formatter.normalize(this.label, this.getClass().getSimpleName()), this.matched, this.description), this.traceable);
            action.run();
        }
        return this;
    }

    /**
     * Executes default action if no of the cases are true
     * @param action action to execute
     * @return instance
     */
    public Switch defaultOf(Runnable action){
        this.matched = false;
        log(this.logger, LogLevel.DEBUG, String.format("[ %s ] Executing DefaultOf block (matched=%s), (description=%s)",
                Formatter.normalize(this.label, this.getClass().getSimpleName()), this.matched, this.description), this.traceable);
        action.run();
        return this;
    }

    /**
     * Throws exception
     *
     * @param exception exception to throw
     */
    public void orThrows(Supplier<? extends RuntimeException> exception) {
        if (!this.matched) {
            log(this.logger, LogLevel.DEBUG, String.format("[ %s ] Throwing Exception (exception=%s), (matched=%s), (description=%s)",
                    Formatter.normalize(this.label, this.getClass().getSimpleName()), exception.getClass().getSimpleName(), this.matched, this.description), this.traceable);
            throw exception.get();
        }
    }

    @Override
    public Switch withLogger(ActumLogger logger) {
        this.logger = logger;
        return this;
    }

    @Override
    public Switch trace() {
        this.traceable = true;
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
    public Switch label(String label) {
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
    public Switch describe(String describe) {
        this.description = describe == null ? "No description provide!" : describe;
        return null;
    }

    /**
     * Used for side effects or debugging
     *
     * @param action action
     * @return action result
     */
    @Override
    public Switch peek(Runnable action) {
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
    public Switch view(Consumer<Switch> consumer) {
        consumer.accept(this);
        return this;
    }
}
