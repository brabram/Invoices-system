package pl.coderstrust.database.nosql.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Objects;

@JsonDeserialize(builder = NoSqlCompany.Builder.class)
public class NoSqlCompany {

  private final String name;
  private final String taxIdentificationNumber;
  private final NoSqlAccountNumber accountNumber;
  private final NoSqlContactDetails contactDetails;

  @PersistenceConstructor
  private NoSqlCompany(String name, String taxIdentificationNumber, NoSqlAccountNumber accountNumber, NoSqlContactDetails contactDetails) {
    this.name = name;
    this.taxIdentificationNumber = taxIdentificationNumber;
    this.accountNumber = accountNumber;
    this.contactDetails = contactDetails;
  }

  protected NoSqlCompany(NoSqlCompany.Builder builder) {
    this.name = builder.name;
    this.taxIdentificationNumber = builder.taxIdentificationNumber;
    this.accountNumber = builder.accountNumber;
    this.contactDetails = builder.contactDetails;
  }

  public static NoSqlCompany.Builder builder() {
    return new NoSqlCompany.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private String name;
    private String taxIdentificationNumber;
    private NoSqlAccountNumber accountNumber;
    private NoSqlContactDetails contactDetails;

    public NoSqlCompany.Builder withName(String name) {
      this.name = name;
      return this;
    }

    public NoSqlCompany.Builder withTaxIdentificationNumber(String taxIdentificationNumber) {
      this.taxIdentificationNumber = taxIdentificationNumber;
      return this;
    }

    public NoSqlCompany.Builder withAccountNumber(NoSqlAccountNumber accountNumber) {
      this.accountNumber = accountNumber;
      return this;
    }

    public NoSqlCompany.Builder withContactDetails(NoSqlContactDetails contactDetails) {
      this.contactDetails = contactDetails;
      return this;
    }

    public NoSqlCompany build() {
      return new NoSqlCompany( this );
    }
  }

  public String getName() {
    return name;
  }

  public String getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  public NoSqlAccountNumber getAccountNumber() {
    return accountNumber;
  }

  public NoSqlContactDetails getContactDetails() {
    return contactDetails;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NoSqlCompany noSqlCompany = (NoSqlCompany) o;
    return Objects.equals(name, noSqlCompany.name)
        && Objects.equals(taxIdentificationNumber, noSqlCompany.taxIdentificationNumber)
        && Objects.equals(accountNumber, noSqlCompany.accountNumber)
        && Objects.equals(contactDetails, noSqlCompany.contactDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, taxIdentificationNumber, accountNumber, contactDetails);
  }

  @Override
  public String toString() {
    return "NoSqlCompany{" +
            "name='" + name + '\'' +
            ", taxIdentificationNumber='" + taxIdentificationNumber + '\'' +
            ", accountNumber=" + accountNumber +
            ", contactDetails=" + contactDetails +
            '}';
  }
}
