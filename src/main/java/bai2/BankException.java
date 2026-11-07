package bai2;

/** Base exception for rejected bank operations. */
public class BankException extends Exception {
  private static final long serialVersionUID = 1L;

  /** Creates an exception with the human-readable reason. */
  public BankException(String message) {
    super(message);
  }
}
