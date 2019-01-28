package pl.coderstrust.model;

import java.util.Objects;
import javax.persistence.Entity;

@Entity
public class AccountNumber {
  private Long id;
  private String ibanNumber;
  private String localNumber;

  protected AccountNumber() {
  }

  public AccountNumber(Long id, String ibanNumber, String localNumber) {
    this.id = id;
    this.ibanNumber = ibanNumber;
    this.localNumber = localNumber;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    return Objects.equals(id, that.id) &&
        Objects.equals(ibanNumber, that.ibanNumber) &&
        Objects.equals(localNumber, that.localNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ibanNumber, localNumber);
  }

  @Override
  public String toString() {
    return
  }
}