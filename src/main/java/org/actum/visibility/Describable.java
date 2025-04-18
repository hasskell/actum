package org.actum.visibility;

/**
 * Provide capabilities to describe current condition
 * @param <T> type of description
 */
public interface Describable<T>{
    /**
     * Labels current condition
     * @param label label
     * @return label
     */
    T label(String label);

    /**
     * Adds description to condition
     * @param describe description message
     * @return description
     */
    T describe(String describe);
}
