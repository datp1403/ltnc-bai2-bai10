package bai2;

/** Ho tro an bot so tai khoan trong log. */
final class LogSupport {

  private LogSupport() {
  }

  /** Chi hien thi bon so cuoi tai khoan. */
  static String maskAccountNumber(long accountNumber) {
    String digits = Long.toString(accountNumber);
    return "****" + digits.substring(Math.max(0, digits.length() - 4));
  }
}
