package pl.coderstrust.database.nosql.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Objects;
import org.springframework.data.annotation.PersistenceConstructor;

@JsonDeserialize(builder = NoSqlAccountNumber.Builder.class)
public class NoSqlAccountNumber {

  private final String ibanNumber;
  private final String localNumber;

  @PersistenceConstructor
  private NoSqlAccountNumber(String ibanNumber, String localNumber) {
    this.ibanNumber = ibanNumber;
    this.localNumber = localNumber;
  }

  protected NoSqlAccountNumber(NoSqlAccountNumber.Builder builder) {
    this.ibanNumber = builder.ibanNumber;
    this.localNumber = builder.localNumber;
  }

  public static NoSqlAccountNumber.Builder builder() {
    return new NoSqlAccountNumber.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private String ibanNumber;
    private String localNumber;

    public Builder withIbanNumber(String ibanNumber) {
      this.ibanNumber = ibanNumber;
      return this;
    }

    public Builder withLocalNumber(String localNumber) {
      this.localNumber = localNumber;
      return this;
    }

    public NoSqlAccountNumber build() {
      return new NoSqlAccountNumber(this);
    }
  }

  public String getIbanNumber() {
    return ibanNumber;
  }

  public String getLocalNumber() {
    return localNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof NoSqlAccountNumber)) {
      return false;
    }
    NoSqlAccountNumber that = (NoSqlAccountNumber) o;
    return Objects.equals(ibanNumber, that.ibanNumber)
        && Objects.equals(localNumber, that.localNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ibanNumber, localNumber);
  }

  @Override
  public String toString() {
    return "NoSqlAccountNumber{"
        + "ibanNumber='" + ibanNumber + '\''
        + ", localNumber='" + localNumber + '\''
        + '}';
  }
}
