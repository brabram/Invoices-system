package pl.coderstrust.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

@JsonDeserialize(builder = Company.Builder.class)
public final class Company {

  @ApiModelProperty(value = "The id of company.", dataType = "Long", position = -1)
  private final Long id;

  @ApiModelProperty(value = "Name of the company", example = "Jan Kowalski - Transport & Logistics Services")
  private final String name;

  @ApiModelProperty(value = "Tax identification number, 10 digits without spaces", example = "2541278654")
  private final String taxIdentificationNumber;

  private final AccountNumber accountNumber;

  private final ContactDetails contactDetails;

  protected Company(Company.Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.taxIdentificationNumber = builder.taxIdentificationNumber;
    this.accountNumber = builder.accountNumber;
    this.contactDetails = builder.contactDetails;
  }

  public static Company.Builder builder() {
    return new Company.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private Long id;
    private String name;
    private String taxIdentificationNumber;
    private AccountNumber accountNumber;
    private ContactDetails contactDetails;

    public Company.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Company.Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Company.Builder withTaxIdentificationNumber(String taxIdentificationNumber) {
      this.taxIdentificationNumber = taxIdentificationNumber;
      return this;
    }

    public Company.Builder withAccountNumber(AccountNumber accountNumber) {
      this.accountNumber = accountNumber;
      return this;
    }

    public Company.Builder withContactDetails(ContactDetails contactDetails) {
      this.contactDetails = contactDetails;
      return this;
    }

    public Company build() {
      return new Company(this);
    }
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  public AccountNumber getAccountNumber() {
    return accountNumber;
  }

  public ContactDetails getContactDetails() {
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
    Company company = (Company) o;
    return Objects.equals(id, company.id)
        && Objects.equals(name, company.name)
        && Objects.equals(taxIdentificationNumber, company.taxIdentificationNumber)
        && Objects.equals(accountNumber, company.accountNumber)
        && Objects.equals(contactDetails, company.contactDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, taxIdentificationNumber, accountNumber, contactDetails);
  }

  @Override
  public String toString() {
    return "Company{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", taxIdentificationNumber='" + taxIdentificationNumber + '\''
        + ", accountNumber=" + accountNumber
        + ", contactDetails=" + contactDetails
        + '}';
  }
}
