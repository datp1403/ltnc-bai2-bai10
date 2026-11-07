package bai2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/** Kiem tra them cac truong hop dac biet cua BankSystem. */
class BankSystemExtendedTest {

  @Test
  void customerAccessorsAndDefaultConstructorWork() {
    Customer customer = new Customer();
    assertEquals(0L, customer.getIdNumber());
    assertEquals("", customer.getFullName());
    customer.setIdNumber(123456789L);
    customer.setFullName("Lan");
    assertEquals(123456789L, customer.getIdNumber());
    assertEquals("Lan", customer.getFullName());
    assertTrue(customer.getCustomerInfo().contains("Lan"));
    assertTrue(customer.getAccountList().isEmpty());
  }

  @Test
  void customerAccountAddRemovePreventsDuplicatesAndNulls() {
    Customer customer = new Customer(123456789L, "A");
    CheckingAccount account = new CheckingAccount(10L, 100.0);
    customer.addAccount(null);
    customer.addAccount(account);
    customer.addAccount(account);
    assertEquals(1, customer.getAccountList().size());
    customer.removeAccount(null);
    customer.removeAccount(account);
    assertTrue(customer.getAccountList().isEmpty());
    customer.setAccountList(new ArrayList<>(Arrays.asList(account)));
    assertEquals(1, customer.getAccountList().size());
    customer.setAccountList(null);
    assertTrue(customer.getAccountList().isEmpty());
  }

  @Test
  void accountAccessorsAndHistoryMutationWork() {
    CheckingAccount account = new CheckingAccount(11L, 150.0);
    account.setAccountNumber(22L);
    assertEquals(22L, account.getAccountNumber());
    Transaction tx = new Transaction(1, 10.0, 150.0, 160.0);
    account.addTransaction(null);
    account.addTransaction(tx);
    assertEquals(1, account.getTransactionList().size());
    account.setTransactionList(new ArrayList<>(Arrays.asList(tx, tx)));
    assertEquals(2, account.getTransactionList().size());
    assertTrue(account.getTransactionHistory().contains("\n"));
    account.setTransactionList(null);
    assertTrue(account.getTransactionList().isEmpty());
  }

  @Test
  void checkingRejectsNonPositiveDepositAndOverdraw() {
    CheckingAccount account = new CheckingAccount(1234L, 20.0);
    account.deposit(-100.0);
    account.deposit(0.0);
    account.withdraw(0.0);
    account.withdraw(21.0);
    assertEquals(20.0, account.getBalance());
    assertTrue(account.getTransactionList().isEmpty());
  }

  @Test
  void savingsRejectsInvalidDepositAndWithdrawal() {
    SavingsAccount account = new SavingsAccount(2345L, 5700.0);
    account.deposit(0.0);
    account.withdraw(-5.0);
    account.withdraw(1001.0);
    account.withdraw(701.0);
    assertEquals(5700.0, account.getBalance());
    assertTrue(account.getTransactionList().isEmpty());
    account.withdraw(700.0);
    assertEquals(5000.0, account.getBalance());
    account.deposit(40.0);
    assertEquals(5040.0, account.getBalance());
  }

  @Test
  void transactionAccessorsAndTypeLabelsWork() {
    Transaction transaction = new Transaction(1, 20.0, 100.0, 120.0);
    transaction.setType(Transaction.TYPE_WITHDRAW_CHECKING);
    transaction.setAmount(30.0);
    transaction.setInitialBalance(120.0);
    transaction.setFinalBalance(90.0);
    assertEquals(Transaction.TYPE_WITHDRAW_CHECKING, transaction.getType());
    assertEquals(30.0, transaction.getAmount());
    assertEquals(120.0, transaction.getInitialBalance());
    assertEquals(90.0, transaction.getFinalBalance());
    assertEquals("Nạp tiền vãng lai", Transaction.getTypeString(1));
    assertEquals("Rút tiền vãng lai", Transaction.getTypeString(2));
    assertEquals("Nạp tiền tiết kiệm", Transaction.getTypeString(3));
    assertEquals("Rút tiền tiết kiệm", Transaction.getTypeString(4));
    assertEquals("Không rõ", Transaction.getTypeString(999));
    assertTrue(transaction.getTransactionSummary().contains("$90.00"));
  }

  @Test
  void accountEqualityConsidersNumberAndNull() {
    Account first = new CheckingAccount(1234L, 100.0);
    Account second = new SavingsAccount(1234L, 6000.0);
    Account different = new CheckingAccount(4321L, 100.0);
    assertEquals(first, first);
    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
    assertNotEquals(first, different);
    assertFalse(first.equals("text"));
    assertFalse(first.equals(null));
  }

  @Test
  void importSkipsUnknownTypeBadNumbersAndMalformedLines() {
    String data = "Nam 111111111\n"
        + "badrow\n"
        + "222 X 500\n"
        + "oops CHECKING 400\n"
        + "222 CHECKING invalid\n"
        + "444 CHECKING 50\n"
        + "444 CHECKING 50\n";
    Bank bank = new Bank();
    bank.readCustomerList(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
    assertEquals(1, bank.getCustomerList().size());
    assertEquals(1, bank.getCustomerList().get(0).getAccountList().size());
  }

  @Test
  void importHandlesMissingStreamAndEmptyDirectory() {
    Bank bank = new Bank();
    bank.readCustomerList(null);
    assertTrue(bank.getCustomerList().isEmpty());
    assertEquals("", bank.getCustomersInfoByIdOrder());
    assertEquals("", bank.getCustomersInfoByNameOrder());
    bank.setCustomerList(new ArrayList<>());
    assertTrue(bank.getCustomerList().isEmpty());
    bank.setCustomerList(null);
    assertTrue(bank.getCustomerList().isEmpty());
  }

  @Test
  void importHandlesInputOutputErrorGracefully() {
    InputStream broken = new InputStream() {
      @Override
      public int read() throws IOException {
        throw new IOException("Simulated I/O failure");
      }
    };
    Bank bank = new Bank();
    bank.readCustomerList(broken);
    assertNotNull(bank.getCustomerList());
  }

  @Test
  void exceptionClassesCarryTheirMessages() {
    BankException generic = new BankException("Error");
    assertEquals("Error", generic.getMessage());
    assertTrue(new InvalidFundingAmountException(-1).getMessage().contains("-1.00"));
    assertTrue(new InsufficientFundsException(10).getMessage().contains("10.00"));
  }

  @Test
  void bankSystemDemoCanRunWithoutRelyingOnAnIde() {
    BankSystemDemo.main(new String[0]);
    assertEquals("****1234", LogSupport.maskAccountNumber(1234L));
    assertEquals("****12", LogSupport.maskAccountNumber(12L));
  }
}
