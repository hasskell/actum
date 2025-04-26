package org.actum.conditional.core;

import org.actum.logger.ActumLogger;
import org.actum.logger.LoggerSupport;
import org.actum.visibility.Debuggable;
import org.actum.visibility.Describable;
import org.actum.visibility.Traceable;
import org.actum.visibility.Viewable;

import java.util.function.Consumer;

public class Switch implements Debuggable, LoggerSupport<Switch>, Traceable<Switch>, Describable<Switch>, Viewable<Switch> {
    private boolean matched;
    private String label;
    private String description;
    private boolean traceable = false;
    private ActumLogger logger = ((logLevel, message) -> {});
    private Object input;

    private Switch(Object input){
        this.input = input;
    }

    public static Switch on(Object input){
        return new Switch(input);
    }

    public Switch caseOf(Object match, Runnable action){
        return this;
    }

    public Switch defaultOf(Runnable action){
        return this;
    }

    @Override
    public Switch withLogger(ActumLogger logger) {
        return null;
    }

    @Override
    public Switch trace() {
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
    public Switch label(String label) {
        return null;
    }

    /**
     * Adds description to condition
     *
     * @param describe description message
     * @return description
     */
    @Override
    public Switch describe(String describe) {
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
        return null;
    }

    /**
     * Used for side effects or debugging
     *
     * @param consumer consumer
     * @return consumer result
     */
    @Override
    public Switch view(Consumer<Switch> consumer) {
        return null;
    }
}
