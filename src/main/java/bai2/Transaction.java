package bai2;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** A deposit or withdrawal and the account balances on either side. */
public class Transaction {
  public static final int TYPE_DEPOSIT_CHECKING = 1;
  public static final int TYPE_WITHDRAW_CHECKING = 2;
  public static final int TYPE_DEPOSIT_SAVINGS = 3;
  public static final int TYPE_WITHDRAW_SAVINGS = 4;

  private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

  private int type;
  private double amount;
  private double initialBalance;
  private double finalBalance;

  /** Creates a transaction with its type, amount, and before/after balances. */
  public Transaction(int type, double amount, double initialBalance, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.initialBalance = initialBalance;
    this.finalBalance = finalBalance;
  }

  /** Gets the type. */
  public int getType() {
    return type;
  }

  /** Updates the type. */
  public void setType(int type) {
    this.type = type;
  }

  /** Gets the transaction amount. */
  public double getAmount() {
    return amount;
  }

  /** Updates the transaction amount. */
  public void setAmount(double amount) {
    this.amount = amount;
  }

  /** Gets the starting balance. */
  public double getInitialBalance() {
    return initialBalance;
  }

  /** Updates the starting balance. */
  public void setInitialBalance(double initialBalance) {
    this.initialBalance = initialBalance;
  }

  /** Gets the ending balance. */
  public double getFinalBalance() {
    return finalBalance;
  }

  /** Updates the ending balance. */
  public void setFinalBalance(double finalBalance) {
    this.finalBalance = finalBalance;
  }

  /** Gets the Vietnamese label of a transaction type. */
  public static String getTypeString(int transactionType) {
    switch (transactionType) {
      case TYPE_DEPOSIT_CHECKING:
        return "Nạp tiền vãng lai";
      case TYPE_WITHDRAW_CHECKING:
        return "Rút tiền vãng lai";
      case TYPE_DEPOSIT_SAVINGS:
        return "Nạp tiền tiết kiệm";
      case TYPE_WITHDRAW_SAVINGS:
        return "Rút tiền tiết kiệm";
      default:
        return "Không rõ";
    }
  }

  /** Returns the original, user-facing transaction description. */
  public String getTransactionSummary() {
    LOGGER.debug("Generating transaction summary: type={}", type);
    return "- Kiểu giao dịch: " + getTypeString(type)
        + ". Số dư ban đầu: $" + formatMoney(initialBalance)
        + ". Số tiền: $" + formatMoney(amount)
        + ". Số dư cuối: $" + formatMoney(finalBalance) + ".";
  }

  private static String formatMoney(double value) {
    return String.format(Locale.US, "%.2f", value);
  }
}
