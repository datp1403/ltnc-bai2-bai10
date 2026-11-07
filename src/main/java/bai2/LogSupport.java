package bai2;

/** Small formatting utilities for non-sensitive audit logs. */
final class LogSupport {

  private LogSupport() {
  }

  /** Avoid recording a full account identifier in log files. */
  static String maskAccountNumber(long accountNumber) {
    String digits = Long.toString(accountNumber);
    return "****" + digits.substring(Math.max(0, digits.length() - 4));
  }
}
