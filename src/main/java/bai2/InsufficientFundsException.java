package bai2;

import java.util.Locale;

/** Indicates that withdrawing would violate an account balance constraint. */
public class InsufficientFundsException extends BankException {
  private static final long serialVersionUID = 1L;

  /** Creates an exception for the rejected amount. */
  public InsufficientFundsException(double amount) {
    super("Số dư tài khoản không đủ $"
        + String.format(Locale.US, "%.2f", amount)
        + " để thực hiện giao dịch");
  }
}
