package com.lab;

/** Calculates shipping fees by weight and delivery class. */
public class ShippingCalculator {

  /** Returns the fee for a positive weight and a supported delivery class. */
  public double calculate(double weight, String type) {
    if (weight <= 0) {
      throw new IllegalArgumentException("Weight must be positive");
    }
    if ("EXPRESS".equals(type)) {
      return weight * 5000 + 20000;
    }
    if ("STANDARD".equals(type)) {
      return weight * 3000;
    }
    throw new IllegalArgumentException("Unknown type: " + type);
  }
}
