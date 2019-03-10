package pl.coderstrust.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

@JsonDeserialize(builder = AccountNumber.Builder.class)
public final class AccountNumber {

  @ApiModelProperty(value = "The id of account number.", position = -1)
  private final Long id;

  @ApiModelProperty(value = "26 digit account number with country code", example = "PL19200000000120067894552302")
  private final String ibanNumber;

  @ApiModelProperty(value = "26 digit account number", example = "'19200000000120067894552302'")
  private final String localNumber;

  protected AccountNumber(AccountNumber.Builder builder) {
    this.id = builder.id;
    this.ibanNumber = builder.ibanNumber;
    this.localNumber = builder.localNumber;
  }

  public static AccountNumber.Builder builder() {
    return new AccountNumber.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private Long id;
    private String ibanNumber;
    private String localNumber;

    public AccountNumber.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public AccountNumber.Builder withIbanNumber(String ibanNumber) {
      this.ibanNumber = ibanNumber;
      return this;
    }

    public AccountNumber.Builder withLocalNumber(String localNumber) {
      this.localNumber = localNumber;
      return this;
    }

    public AccountNumber build() {
      return new AccountNumber(this);
    }
  }

  public Long getId() {
    return id;
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
    if (!(o instanceof AccountNumber)) {
      return false;
    }
    AccountNumber that = (AccountNumber) o;
    return Objects.equals(id, that.id)
        && Objects.equals(ibanNumber, that.ibanNumber)
        && Objects.equals(localNumber, that.localNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ibanNumber, localNumber);
  }

  @Override
  public String toString() {
    return "AccountNumber{"
        + "id=" + id
        + ", ibanNumber='" + ibanNumber + '\''
        + ", localNumber='" + localNumber + '\''
        + '}';
  }
}
