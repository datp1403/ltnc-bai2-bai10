package bai2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Checking account with ordinary deposit and withdrawal rules. */
public class CheckingAccount extends Account {
  private static final Logger LOGGER = LoggerFactory.getLogger(CheckingAccount.class);

  /** Creates a checking account. */
  public CheckingAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();
      addTransaction(new Transaction(
          Transaction.TYPE_DEPOSIT_CHECKING, amount, initialBalance, finalBalance));
      LOGGER.info("Deposit completed: account={}, amount={}, balance={}",
          LogSupport.maskAccountNumber(getAccountNumber()), amount, finalBalance);
    } catch (BankException exception) {
      LOGGER.warn("Deposit rejected: account={}, amount={}, reason={}",
          LogSupport.maskAccountNumber(getAccountNumber()), amount, exception.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      doWithdrawing(amount);
      double finalBalance = getBalance();
      addTransaction(new Transaction(
          Transaction.TYPE_WITHDRAW_CHECKING, amount, initialBalance, finalBalance));
      LOGGER.info("Withdrawal completed: account={}, amount={}, balance={}",
          LogSupport.maskAccountNumber(getAccountNumber()), amount, finalBalance);
    } catch (BankException exception) {
      LOGGER.warn("Withdrawal rejected: account={}, amount={}, reason={}",
          LogSupport.maskAccountNumber(getAccountNumber()), amount, exception.getMessage());
    }
  }
}
