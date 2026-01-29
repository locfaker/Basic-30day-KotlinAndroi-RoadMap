/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  DAY 26 - BÀI 1: UNIT TEST                                    ║
 * ║                                                               ║
 * ║  File: app/src/test/java/.../ExampleUnitTest.kt               ║
 * ║  (Thư mục test, KHÔNG phải androidTest)                       ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */

package com.example.myapplication

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

// Class cần test
class Calculator {
    fun add(a: Int, b: Int): Int = a + b
    fun subtract(a: Int, b: Int): Int = a - b
    fun multiply(a: Int, b: Int): Int = a * b
    fun divide(a: Int, b: Int): Int {
        if (b == 0) throw IllegalArgumentException("Cannot divide by zero")
        return a / b
    }
}

// Test class
class CalculatorTest {
    
    private lateinit var calculator: Calculator
    
    @Before
    fun setup() {
        // Chạy trước mỗi test
        calculator = Calculator()
    }
    
    @Test
    fun `add should return sum of two numbers`() {
        val result = calculator.add(2, 3)
        assertEquals(5, result)
    }
    
    @Test
    fun `subtract should return difference`() {
        val result = calculator.subtract(5, 3)
        assertEquals(2, result)
    }
    
    @Test
    fun `multiply should return product`() {
        val result = calculator.multiply(4, 3)
        assertEquals(12, result)
    }
    
    @Test
    fun `divide should return quotient`() {
        val result = calculator.divide(10, 2)
        assertEquals(5, result)
    }
    
    @Test(expected = IllegalArgumentException::class)
    fun `divide by zero should throw exception`() {
        calculator.divide(10, 0)
    }
}

// Test validation function
class ValidationTest {
    
    @Test
    fun `email should be valid`() {
        assertTrue(isValidEmail("test@example.com"))
        assertTrue(isValidEmail("user.name@domain.co"))
    }
    
    @Test
    fun `email should be invalid`() {
        assertFalse(isValidEmail(""))
        assertFalse(isValidEmail("invalid"))
        assertFalse(isValidEmail("@domain.com"))
    }
    
    private fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
    }
}

/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║  GIẢI THÍCH UNIT TEST:                                        ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║                                                               ║
 * ║  THƯ MỤC:                                                     ║
 * ║  src/test/java/         → Unit tests (chạy trên JVM)          ║
 * ║  src/androidTest/java/  → Instrumented tests (chạy trên device)║
 * ║                                                               ║
 * ║  ANNOTATIONS:                                                 ║
 * ║  @Test      → Đánh dấu hàm là test                            ║
 * ║  @Before    → Chạy trước mỗi test                             ║
 * ║  @After     → Chạy sau mỗi test                               ║
 * ║  @BeforeClass → Chạy 1 lần trước tất cả tests                 ║
 * ║                                                               ║
 * ║  ASSERTIONS:                                                  ║
 * ║  assertEquals(expected, actual)                               ║
 * ║  assertTrue(condition)                                        ║
 * ║  assertFalse(condition)                                       ║
 * ║  assertNull(obj)                                              ║
 * ║  assertNotNull(obj)                                           ║
 * ║  assertThrows(Exception::class) { }                           ║
 * ║                                                               ║
 * ║  CHẠY TEST:                                                   ║
 * ║  → Right-click test file → Run                                ║
 * ║  → ./gradlew test                                             ║
 * ║                                                               ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
