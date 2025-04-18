package org.actum.core;

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
}