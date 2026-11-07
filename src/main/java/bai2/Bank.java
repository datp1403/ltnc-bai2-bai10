package bai2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Bank directory and parser for the legacy customer data format. */
public class Bank {
  private static final Logger LOGGER = LoggerFactory.getLogger(Bank.class);
  private static final String CUSTOMER_ID_PATTERN = "\\d{9}";
  private static final String FIELD_SEPARATOR = "\\s+";

  private List<Customer> customers;

  /** Creates a bank with an empty customer directory. */
  public Bank() {
    this.customers = new ArrayList<>();
  }

  /** Gets the customer directory. */
  public List<Customer> getCustomerList() {
    return customers;
  }

  /** Replaces the directory or resets it if null. */
  public void setCustomerList(List<Customer> customerList) {
    this.customers = customerList == null ? new ArrayList<>() : customerList;
  }

  /**
   * Reads customers and accounts from the legacy text input format.
   * The input stream remains owned by the caller and is not closed here.
   *
   * @param inputStream caller-owned UTF-8 input data
   */
  public void readCustomerList(InputStream inputStream) {
    if (inputStream == null) {
      LOGGER.warn("Customer import skipped: input stream is null");
      return;
    }
    LOGGER.info("Customer import started");
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    Customer currentCustomer = null;
    int lineNumber = 0;
    int addedCustomers = 0;
    int addedAccounts = 0;
    try {
      String line;
      while ((line = reader.readLine()) != null) {
        lineNumber++;
        line = line.trim();
        if (line.isEmpty()) {
          continue;
        }
        int lastSpaceIndex = line.lastIndexOf(' ');
        if (lastSpaceIndex <= 0) {
          LOGGER.warn("Customer import skipped malformed line={}", lineNumber);
          continue;
        }
        String lastToken = line.substring(lastSpaceIndex + 1).trim();
        if (lastToken.matches(CUSTOMER_ID_PATTERN)) {
          String name = line.substring(0, lastSpaceIndex).trim();
          currentCustomer = new Customer(Long.parseLong(lastToken), name);
          customers.add(currentCustomer);
          addedCustomers++;
          LOGGER.info("Customer imported: record={}", addedCustomers);
        } else if (currentCustomer != null && addAccountLine(currentCustomer, line, lineNumber)) {
          addedAccounts++;
        }
      }
      LOGGER.info("Customer import finished: customers={}, accounts={}",
          addedCustomers, addedAccounts);
    } catch (IOException exception) {
      LOGGER.error("Customer import failed at line={}", lineNumber, exception);
    }
  }

  /** Returns customers ordered by ID (also sorts the stored directory, as before). */
  public String getCustomersInfoByIdOrder() {
    customers.sort(Comparator.comparingLong(Customer::getIdNumber));
    return joinCustomerInfo(customers);
  }

  /** Returns customers ordered by name, breaking ties by ID. */
  public String getCustomersInfoByNameOrder() {
    List<Customer> sorted = new ArrayList<>(customers);
    sorted.sort(Comparator.comparing(Customer::getFullName)
        .thenComparingLong(Customer::getIdNumber));
    return joinCustomerInfo(sorted);
  }

  private boolean addAccountLine(Customer customer, String line, int lineNumber) {
    String[] parts = line.split(FIELD_SEPARATOR);
    if (parts.length < 3) {
      LOGGER.warn("Customer import skipped malformed account line={}", lineNumber);
      return false;
    }
    try {
      long accountNumber = Long.parseLong(parts[0]);
      double balance = Double.parseDouble(parts[2]);
      Account account;
      if (Account.CHECKING_TYPE.equals(parts[1])) {
        account = new CheckingAccount(accountNumber, balance);
      } else if (Account.SAVINGS_TYPE.equals(parts[1])) {
        account = new SavingsAccount(accountNumber, balance);
      } else {
        LOGGER.warn("Customer import skipped account with unknown type at line={}", lineNumber);
        return false;
      }
      int previousCount = customer.getAccountList().size();
      customer.addAccount(account);
      LOGGER.debug("Imported account: account={}, line={}",
          LogSupport.maskAccountNumber(accountNumber), lineNumber);
      return customer.getAccountList().size() > previousCount;
    } catch (NumberFormatException exception) {
      LOGGER.warn("Customer import skipped account with invalid number at line={}", lineNumber);
      return false;
    }
  }

  private static String joinCustomerInfo(List<Customer> customerList) {
    return customerList.stream()
        .map(Customer::getCustomerInfo)
        .collect(Collectors.joining("\n"));
  }
}
