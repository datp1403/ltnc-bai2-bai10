package bai2;

import java.util.Locale;

/** Indicates that the amount of money is invalid. */
public class InvalidFundingAmountException extends BankException {
  private static final long serialVersionUID = 1L;

  /** Creates an exception for the rejected amount. */
  public InvalidFundingAmountException(double amount) {
    super("Số tiền không hợp lệ: $" + String.format(Locale.US, "%.2f", amount));
  }
}
