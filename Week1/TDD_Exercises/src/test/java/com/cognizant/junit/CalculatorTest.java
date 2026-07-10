package com.cognizant.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    // Exercise 4: Test Fixtures, Setup and Teardown Methods
    @BeforeAll
    static void initAll() {
        System.out.println("--- @BeforeAll: Executed once before all test methods ---");
    }

    @BeforeEach
    void setUp() {
        System.out.println("--- @BeforeEach: Executed before each test method ---");
        // Arrange
        calculator = new Calculator();
    }

    @AfterEach
    void tearDown() {
        System.out.println("--- @AfterEach: Executed after each test method ---");
        calculator = null;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("--- @AfterAll: Executed once after all test methods ---");
    }

    // Exercise 1: Setting Up JUnit & Basic Test
    @Test
    @DisplayName("Test addition of two positive numbers")
    void testAdd() {
        // Exercise 4: Arrange-Act-Assert (AAA) Pattern
        
        // Arrange (done in @BeforeEach)
        int a = 5;
        int b = 3;

        // Act
        int result = calculator.add(a, b);

        // Assert (Exercise 3: Assertions)
        assertEquals(8, result, "5 + 3 should equal 8");
    }

    // Exercise 3: Assertions in JUnit
    @Test
    @DisplayName("Test subtraction resulting in a negative number")
    void testSubtract() {
        // Act
        int result = calculator.subtract(3, 5);

        // Assert
        assertTrue(result < 0, "Result should be negative");
        assertEquals(-2, result);
    }

    @Test
    @DisplayName("Test multiplication with zero")
    void testMultiply() {
        // Act
        int result = calculator.multiply(10, 0);

        // Assert
        assertEquals(0, result, "Multiplying by zero should be zero");
    }

    @Test
    @DisplayName("Test division throws exception when dividing by zero")
    void testDivideByZero() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        });

        assertEquals("Cannot divide by zero", exception.getMessage());
    }
}
