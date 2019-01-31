package pl.coderstrust.model;

import java.util.Objects;

public class AccountNumber {
  private String ibanNumber;
  private String localNumber;

  protected AccountNumber() {
  }

  public AccountNumber(String ibanNumber, String localNumber) {
    this.ibanNumber = ibanNumber;
    this.localNumber = localNumber;
  }

  public String getIbanNumber() {
    return ibanNumber;
  }

  public void setIbanNumber(String ibanNumber) {
    this.ibanNumber = ibanNumber;
  }

  public String getLocalNumber() {
    return localNumber;
  }

  public void setLocalNumber(String localNumber) {
    this.localNumber = localNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AccountNumber)) {
      return false;
    }
    AccountNumber that = (AccountNumber) o;
    return Objects.equals(ibanNumber, that.ibanNumber)
        && Objects.equals(localNumber, that.localNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ibanNumber, localNumber);
  }

  @Override
  public String toString() {
    return String.format("ibanNumber: %s, localNumber: %s", ibanNumber, localNumber);
  }
}
