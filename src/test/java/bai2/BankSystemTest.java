package bai2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/** Regression tests for account rules and customer import. */
class BankSystemTest {

  @Test
  void checkingAccountTracksValidTransactions() {
    CheckingAccount account = new CheckingAccount(123456789L, 2000.0);
    account.deposit(500.0);
    account.withdraw(200.0);

    assertEquals(2300.0, account.getBalance());
    assertEquals(2, account.getTransactionList().size());
    assertEquals(Transaction.TYPE_DEPOSIT_CHECKING, account.getTransactionList().get(0).getType());
    assertEquals(Transaction.TYPE_WITHDRAW_CHECKING, account.getTransactionList().get(1).getType());
    assertTrue(account.getTransactionHistory().contains("Số dư cuối: $2300.00"));
  }

  @Test
  void invalidCheckingOperationsDoNotChangeBalance() {
    CheckingAccount account = new CheckingAccount(234567890L, 100.0);
    account.deposit(0.0);
    account.withdraw(-2.0);
    account.withdraw(101.0);

    assertEquals(100.0, account.getBalance());
    assertTrue(account.getTransactionList().isEmpty());
  }

  @Test
  void savingsWithdrawalEnforcesBothLimits() {
    SavingsAccount account = new SavingsAccount(345678901L, 6200.0);
    account.withdraw(1001.0);
    account.withdraw(1201.0);
    account.withdraw(1000.0);
    account.withdraw(300.0);

    assertEquals(5200.0, account.getBalance());
    assertEquals(1, account.getTransactionList().size());
    assertEquals(Transaction.TYPE_WITHDRAW_SAVINGS,
        account.getTransactionList().get(0).getType());
  }

  @Test
  void invalidSavingsDepositDoesNotCreateHistory() {
    SavingsAccount account = new SavingsAccount(456789012L, 5200.0);
    account.deposit(-50.0);

    assertEquals(5200.0, account.getBalance());
    assertTrue(account.getTransactionList().isEmpty());
  }

  @Test
  void readCustomerListPreservesCustomerAndAccountData() {
    String input = "Nguyen Van A 123456789\n"
        + "111111 CHECKING 1500\n"
        + "222222 SAVINGS 6500\n"
        + "Tran Thi B 987654321\n"
        + "333333 CHECKING 200\n";
    Bank bank = new Bank();
    bank.readCustomerList(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

    assertEquals(2, bank.getCustomerList().size());
    assertEquals(2, bank.getCustomerList().get(0).getAccountList().size());
    assertEquals(1, bank.getCustomerList().get(1).getAccountList().size());
    assertTrue(bank.getCustomerList().get(0).getAccountList().get(0)
        instanceof CheckingAccount);
    assertTrue(bank.getCustomerList().get(0).getAccountList().get(1)
        instanceof SavingsAccount);
  }

  @Test
  void orderingByNameDoesNotMutateOriginalCustomerOrder() {
    Bank bank = new Bank();
    bank.setCustomerList(Arrays.asList(
        new Customer(20L, "Zed"),
        new Customer(10L, "Amy")));

    String byName = bank.getCustomersInfoByNameOrder();
    assertTrue(byName.indexOf("Amy") < byName.indexOf("Zed"));
    assertEquals("Zed", bank.getCustomerList().get(0).getFullName());
    String byId = bank.getCustomersInfoByIdOrder();
    assertTrue(byId.indexOf("Amy") < byId.indexOf("Zed"));
    assertEquals(10L, bank.getCustomerList().get(0).getIdNumber());
  }

  @Test
  void equalAccountsHaveEqualHashes() {
    Account first = new CheckingAccount(9999L, 100.0);
    Account second = new SavingsAccount(9999L, 8000.0);

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
    assertFalse(first.equals(null));
  }

  @Test
  void malformedCustomerLinesAreSkippedWithoutLosingValidRecords() {
    String input = "Nguyen Van A 123456789\n"
        + "not-an-account\n"
        + "bad CHECKING abc\n"
        + "100000 CHECKING 900\n";
    Bank bank = new Bank();
    bank.readCustomerList(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

    assertEquals(1, bank.getCustomerList().size());
    assertEquals(1, bank.getCustomerList().get(0).getAccountList().size());
  }

  @Test
  void transactionSummaryKeepsTheOriginalOutputFormat() {
    Transaction transaction = new Transaction(
        Transaction.TYPE_DEPOSIT_SAVINGS, 100.0, 6000.0, 6100.0);

    assertEquals(
        "- Kiểu giao dịch: Nạp tiền tiết kiệm. Số dư ban đầu: $6000.00. "
            + "Số tiền: $100.00. Số dư cuối: $6100.00.",
        transaction.getTransactionSummary());
    assertEquals("Không rõ", Transaction.getTypeString(-1));
  }

  @Test
  void accountHistoryIsEmptyWhenThereAreNoTransactions() {
    CheckingAccount account = new CheckingAccount(4444L, 100.0);

    assertEquals("Lịch sử giao dịch của tài khoản 4444:\n", account.getTransactionHistory());
  }
}
