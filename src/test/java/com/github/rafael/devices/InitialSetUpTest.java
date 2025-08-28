package com.github.rafael.devices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitialSetUpTest {

    private final InitialSetUp calculatorService = new InitialSetUp();

    @Test
    void add_shouldReturnSumOfTwoNumbers() {
        assertEquals(5, calculatorService.add(2, 3));
        assertEquals(0, calculatorService.add(-2, 2));
    }

    @Test
    void multiply_shouldReturnProductOfTwoNumbers() {
        assertEquals(6, calculatorService.multiply(2, 3));
        assertEquals(0, calculatorService.multiply(0, 99));
    }

    @Test
    void isEven_shouldDetectEvenNumbers() {
        assertTrue(calculatorService.isEven(4));
        assertFalse(calculatorService.isEven(5));
    }
}