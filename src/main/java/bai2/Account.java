package bai2;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Common data and operations for bank accounts. */
public abstract class Account {
  /** Identifier of a checking account type. */
  public static final String CHECKING_TYPE = "CHECKING";

  /** Identifier of a savings account type. */
  public static final String SAVINGS_TYPE = "SAVINGS";

  private static final Logger LOGGER = LoggerFactory.getLogger(Account.class);

  private long accountNumber;
  private double balance;
  private List<Transaction> transactions;

  /** Creates an account with its number and initial balance. */
  public Account(long accountNumber, double balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.transactions = new ArrayList<>();
  }

  /** Gets the account number. */
  public long getAccountNumber() {
    return accountNumber;
  }

  /** Updates the account number. */
  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  /** Gets the current balance. */
  public double getBalance() {
    return balance;
  }

  /** Updates the internal balance. */
  protected void setBalance(double balance) {
    this.balance = balance;
  }

  /** Gets the current transaction list. */
  public List<Transaction> getTransactionList() {
    return transactions;
  }

  /** Replaces transaction history or resets it if null. */
  public void setTransactionList(List<Transaction> transactionList) {
    this.transactions = transactionList == null ? new ArrayList<>() : transactionList;
  }

  /** Deposits the requested amount if the subclass accepts it. */
  public abstract void deposit(double amount);

  /** Withdraws the requested amount if the subclass accepts it. */
  public abstract void withdraw(double amount);

  /** Adds funds or throws a domain exception for invalid amounts. */
  protected void doDepositing(double amount) throws InvalidFundingAmountException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    balance += amount;
  }

  /** Removes funds or throws a domain exception if the operation cannot proceed. */
  protected void doWithdrawing(double amount) throws BankException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    if (amount > balance) {
      throw new InsufficientFundsException(amount);
    }
    balance -= amount;
  }

  /** Adds a non-null transaction to the history. */
  public void addTransaction(Transaction transaction) {
    if (transaction != null) {
      transactions.add(transaction);
    }
  }

  /** Renders the transaction history in the original text format. */
  public String getTransactionHistory() {
    StringBuilder history = new StringBuilder("Lịch sử giao dịch của tài khoản ")
        .append(accountNumber)
        .append(":\n");
    for (int index = 0; index < transactions.size(); index++) {
      if (index > 0) {
        history.append('\n');
      }
      history.append(transactions.get(index).getTransactionSummary());
    }
    LOGGER.debug("Transaction history requested: account={}, entries={}",
        LogSupport.maskAccountNumber(accountNumber), transactions.size());
    return history.toString();
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Account)) {
      return false;
    }
    Account otherAccount = (Account) other;
    return accountNumber == otherAccount.accountNumber;
  }

  @Override
  public int hashCode() {
    return Long.hashCode(accountNumber);
  }
}
