package org.actum.util;

public class Validator {
    /**
     * Checking null input and propagate to IllegalArgumentException
     * @param input
     */
    public static void checkNotNull(Object input){
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
    }
}
