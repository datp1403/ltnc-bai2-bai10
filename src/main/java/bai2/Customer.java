package bai2;

import java.util.ArrayList;
import java.util.List;

/** Customer details and the accounts that belong to the customer. */
public class Customer {
  private long idNumber;
  private String fullName;
  private List<Account> accountList;

  /** Constructs an empty customer. */
  public Customer() {
    this(0L, "");
  }

  /** Constructs a customer with an ID and full name. */
  public Customer(long idNumber, String fullName) {
    this.idNumber = idNumber;
    this.fullName = fullName;
    this.accountList = new ArrayList<>();
  }

  /** Gets the identification number. */
  public long getIdNumber() {
    return idNumber;
  }

  /** Updates the identification number. */
  public void setIdNumber(long idNumber) {
    this.idNumber = idNumber;
  }

  /** Gets the customer's full name. */
  public String getFullName() {
    return fullName;
  }

  /** Updates the customer's full name. */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /** Gets the customer's accounts. */
  public List<Account> getAccountList() {
    return accountList;
  }

  /** Replaces the customer accounts or resets them if null. */
  public void setAccountList(List<Account> accountList) {
    this.accountList = accountList == null ? new ArrayList<>() : accountList;
  }

  /** Adds an account if it does not already exist. */
  public void addAccount(Account account) {
    if (account != null && !accountList.contains(account)) {
      accountList.add(account);
    }
  }

  /** Removes an account if it is present. */
  public void removeAccount(Account account) {
    if (account != null) {
      accountList.remove(account);
    }
  }

  /** Returns the customer display text. */
  public String getCustomerInfo() {
    return "Số CMND: " + idNumber + ". Họ tên: " + fullName + ".";
  }
}
