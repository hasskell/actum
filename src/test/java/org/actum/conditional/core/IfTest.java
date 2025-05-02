package org.actum.conditional.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IfTest {
    private AtomicInteger result;

    @BeforeEach
    public void setUp() {
        this.result = new AtomicInteger();
    }

    @Test
    public void test_when_then_condition() {
        int expected = 5;
        If.when(true)
                .then(() -> result.set(expected));

        assertEquals(expected, result.get());
    }

    @Test
    public void test_when_else_then_condition() {
        int expected = 5;
        If.when(expected > 10)
                .then(() -> result.set(10))
                .elseThen(() -> result.set(expected));
        assertEquals(expected, result.get());
    }

    @Test
    public void test_when_else_if_then_condition() {
        int expected = 5;
        If.when(expected > 10)
                .then(() -> result.set(10))
                .elseIf(expected < 10, () -> result.set(expected));
        assertEquals(expected, result.get());
    }

    @Test
    public void test_when_else_if_else_if_then_condition() {
        int expected = 5;
        If.when(expected > 10)
                .then(() -> result.set(10))
                .elseIf(expected > 20, () -> result.set(20))
                .elseThen(() -> result.set(expected));
        assertEquals(expected, result.get());
    }

    @Test
    public void test_when_throws() {
        boolean shouldNotThrow = true;
        assertThrows(IllegalArgumentException.class, () -> If.when(!shouldNotThrow)
                .orThrows(IllegalArgumentException::new));
    }

    @Test
    public void test_when_should_not_throw() {
        boolean shouldNotThrow = true;
        assertDoesNotThrow(() -> If.when(shouldNotThrow)
                .orThrows(IllegalArgumentException::new));
    }

    @Test
    public void test_when_inner_then_condition() {
        If.when(true)
                .then(() -> {
                    If.when(true)
                            .then(() -> result.set(10));
                })
                .elseThen(() -> result.set(20));
        assertEquals(10, result.get());
    }

    @Test
    public void test_when_peek_executes_only_if_matched() {
        AtomicInteger peeked = new AtomicInteger();
        If.when(true)
                .peek(() -> peeked.set(1))
                .then(() -> result.set(5));

        assertEquals(1, peeked.get());
        assertEquals(5, result.get());
    }

    @Test
    public void test_when_view_invokes_consumer_with_state() {
        AtomicInteger captured = new AtomicInteger();

        If.when(true)
                .then(() -> result.set(10))
                .view(ifBlock -> {
                    if (ifBlock.isMatched()) {
                        captured.set(1);
                    }
                });

        assertEquals(1, captured.get());
        assertEquals(10, result.get());
    }

    @Test
    public void test_label_and_description_set_correctly() {
        If logic = If.when(true)
                .label("auth-check")
                .describe("Validating login");

        assertEquals("auth-check", logic.getLabel());
        assertEquals("Validating login", logic.getDescription());
    }

    @Test
    public void test_then_null_action_should_throw() {
        assertThrows(IllegalArgumentException.class, () -> If.when(true).then(null));
    }

    @Test
    public void test_else_if_null_action_should_throw() {
        assertThrows(IllegalArgumentException.class, () -> If.when(false).elseIf(true, null));
    }

    @Test
    public void test_else_then_null_action_should_throw() {
        assertThrows(IllegalArgumentException.class, () -> If.when(false).elseThen(null));
    }

    @Test
    public void test_trace_logging_called_when_enabled() {
        AtomicInteger logged = new AtomicInteger();

        If.when(true)
                .trace()
                .withLogger((level, msg) -> logged.incrementAndGet())
                .then(() -> result.set(42));

        assertEquals(1, logged.get()); // logger called
        assertEquals(42, result.get());
    }
}