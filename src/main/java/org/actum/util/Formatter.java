package org.actum.util;

/**
 * Utility class to help sanitize input strings
 */
public class Formatter {
    private static final String DEFAULT_LABEL_PREFIX = "actum";

    /**
     * Normalize string to kebab-case
     * @param input Input string
     * @param defaultValue In case input is not provided
     * @return kebab-case normalized string
     */
    public static String normalize(String input, String defaultValue) {
        if (input == null || input.isBlank()) {
            input = DEFAULT_LABEL_PREFIX + defaultValue;
        }
        String normalized = replace(input);
        return normalized.toLowerCase();
    }

    private static String replace(String input) {
        String normalized = input.replaceAll("_", "-");

        normalized = normalized.replaceAll("([a-z])([A-Z])", "$1-$2");
        normalized = normalized.replaceAll("([A-Z]+)([A-Z][a-z])", "$1-$2");

        normalized = normalized.replaceAll("--+", "-").replaceAll("^-|-$", "");
        return normalized;
    }
}
