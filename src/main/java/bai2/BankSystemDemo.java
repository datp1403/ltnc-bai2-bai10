package bai2;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Chay thu mot vai thao tac co ban cua BankSystem. */
public final class BankSystemDemo {
  private static final Logger LOGGER = LoggerFactory.getLogger(BankSystemDemo.class);

  private BankSystemDemo() {
  }

  /** Nap, rut tien va doc thu danh sach khach hang. */
  public static void main(String[] args) {
    LOGGER.info("BankSystem demonstration started");

    CheckingAccount checking = new CheckingAccount(123456789L, 2000.0);
    checking.deposit(500.0);
    checking.withdraw(250.0);
    checking.withdraw(10000.0);

    SavingsAccount savings = new SavingsAccount(987654321L, 6500.0);
    savings.deposit(200.0);
    savings.withdraw(500.0);
    savings.withdraw(1200.0);

    LOGGER.info("Checking operations finished: transactions={}, balance={}",
        checking.getTransactionList().size(), checking.getBalance());
    LOGGER.info("Savings operations finished: transactions={}, balance={}",
        savings.getTransactionList().size(), savings.getBalance());

    String customers = "Nguyen Van A 123456789\n"
        + "111111 CHECKING 1500\n"
        + "222222 SAVINGS 6500\n";
    Bank bank = new Bank();
    bank.readCustomerList(new ByteArrayInputStream(
        customers.getBytes(StandardCharsets.UTF_8)));
    LOGGER.info("BankSystem demonstration finished: customers={}",
        bank.getCustomerList().size());
  }
}
