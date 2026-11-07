package com.lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Unit tests from the provided broken-pipeline task. */
class ShippingCalculatorTest {
  private final ShippingCalculator calculator = new ShippingCalculator();

  @Test
  void testStandard() {
    assertEquals(15000.0, calculator.calculate(5, "STANDARD"));
  }

  @Test
  void testExpress() {
    assertEquals(45000.0, calculator.calculate(5, "EXPRESS"));
  }

  @Test
  void testInvalidWeight() {
    assertThrows(IllegalArgumentException.class,
        () -> calculator.calculate(-1, "STANDARD"));
  }

  @Test
  void testUnknownType() {
    assertThrows(IllegalArgumentException.class,
        () -> calculator.calculate(5, "ECONOMY"));
  }
}
