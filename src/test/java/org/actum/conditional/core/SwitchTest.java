package org.actum.conditional.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SwitchTest {

    private AtomicInteger result;

    @BeforeEach
    void setUp() {
        result = new AtomicInteger();
    }

    @Test
    void test_case_of_executes_when_matched() {
        Switch.on("A")
                .caseOf("A", () -> result.set(1))
                .caseOf("B", () -> result.set(2)); // should be skipped

        assertEquals(1, result.get());
    }

    @Test
    void test_case_of_does_not_execute_when_not_matched() {
        Switch.on("X")
                .caseOf("A", () -> result.set(1));

        assertEquals(0, result.get()); // unchanged
    }

    @Test
    void test_case_of_then_defaultOf_executes_when_no_match() {
        Switch.on("X")
                .caseOf("A", () -> result.set(1))
                .defaultOf(() -> result.set(9));

        assertEquals(9, result.get());
    }

    @Test
    void test_case_of_then_defaultOf_does_not_run_if_case_matched() {
        Switch.on("Y")
                .caseOf("Y", () -> result.set(7))
                .defaultOf(() -> result.set(99));

        assertEquals(7, result.get()); // default skipped
    }

    @Test
    void test_or_throws_should_throw_when_not_matched() {
        assertThrows(IllegalStateException.class,
                () -> Switch.on("Z")
                        .caseOf("A", () -> {})
                        .orThrows(IllegalStateException::new));
    }

    @Test
    void test_or_throws_should_not_throw_when_matched() {
        assertDoesNotThrow(() ->
                Switch.on("A")
                        .caseOf("A", () -> result.set(5))
                        .orThrows(IllegalStateException::new));

        assertEquals(5, result.get());
    }

    @Test
    void test_peek_runs_when_matched() {
        AtomicInteger peeked = new AtomicInteger();

        Switch.on("yes")
                .caseOf("yes", () -> result.set(100))
                .peek(() -> peeked.set(1));

        assertEquals(1, peeked.get());
        assertEquals(100, result.get());
    }

    @Test
    void test_view_exposes_internal_state() {
        AtomicReference<String> labelOut = new AtomicReference<>();

        Switch logic = Switch.on("foo")
                .label("my-switch")
                .describe("basic switch")
                .caseOf("bar", () -> {})
                .view(sw -> labelOut.set(sw.getLabel()));

        assertEquals("my-switch", labelOut.get());
    }

    @Test
    void test_label_and_description_defaults() {
        Switch logic = Switch.on("x");

        assertTrue(logic.getLabel().contains("Switch"));
        assertEquals("No description provided!", logic.getDescription());
    }

    @Test
    void test_trace_logging_executes_when_enabled() {
        AtomicInteger logged = new AtomicInteger();

        Switch.on("trace")
                .trace()
                .withLogger((level, message) -> logged.incrementAndGet())
                .caseOf("trace", () -> result.set(1));

        assertEquals(1, logged.get());
        assertEquals(1, result.get());
    }
}
