package bai2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Savings account with per-withdrawal and minimum-balance restrictions. */
public class SavingsAccount extends Account {
  private static final double MAX_WITHDRAW_AMOUNT = 1000.0;
  private static final double MIN_BALANCE = 5000.0;
  private static final Logger LOGGER = LoggerFactory.getLogger(SavingsAccount.class);

  /** Creates a savings account. */
  public SavingsAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    LOGGER.debug("Savings deposit started: account={}",
        LogSupport.maskAccountNumber(getAccountNumber()));
    try {
      doDepositing(amount);
      double finalBalance = getBalance();
      addTransaction(new Transaction(
          Transaction.TYPE_DEPOSIT_SAVINGS, amount, initialBalance, finalBalance));
      LOGGER.info("Savings deposit completed: account={}, amount={}, balance={}",
          LogSupport.maskAccountNumber(getAccountNumber()), amount, finalBalance);
    } catch (BankException exception) {
      LOGGER.warn("Savings deposit rejected: account={}, amount={}, reason={}",
          LogSupport.maskAccountNumber(getAccountNumber()), amount, exception.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      if (amount > MAX_WITHDRAW_AMOUNT) {
        throw new InvalidFundingAmountException(amount);
      }
      if (initialBalance - amount < MIN_BALANCE) {
        throw new InsufficientFundsException(amount);
      }
      doWithdrawing(amount);
      double finalBalance = getBalance();
      addTransaction(new Transaction(
          Transaction.TYPE_WITHDRAW_SAVINGS, amount, initialBalance, finalBalance));
      LOGGER.info("Savings withdrawal completed: account={}, amount={}, balance={}",
          LogSupport.maskAccountNumber(getAccountNumber()), amount, finalBalance);
    } catch (BankException exception) {
      LOGGER.warn("Savings withdrawal rejected: account={}, amount={}, reason={}",
          LogSupport.maskAccountNumber(getAccountNumber()), amount, exception.getMessage());
    }
  }
}
