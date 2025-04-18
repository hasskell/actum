package org.actum.visibility;

import java.util.function.Consumer;

/**
 * Provide capabilities to view current condition
 * @param <T> type of view
 */
public interface Viewable<T>{
    /**
     * Used for side effects or debugging
     * @param action action
     * @return action result
     */
    T peak(Runnable action);

    /**
     * Used for side effects or debugging
     * @param consumer consumer
     * @return consumer result
     */
    T view(Consumer<T> consumer);
}
