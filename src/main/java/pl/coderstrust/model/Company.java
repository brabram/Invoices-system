package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public final class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "The id of company.", dataType = "Long", position = -1)
  private final Long id;

  @ApiModelProperty(value = "Name of the company", example = "Jan Kowalski - Transport & Logistics Services")
  private final String name;

  @ApiModelProperty(value = "Tax identification number, 10 digits without spaces", example = "2541278654")
  private final String taxIdentificationNumber;

  @OneToOne(cascade = CascadeType.ALL)
  private final AccountNumber accountNumber;

  @OneToOne(cascade = CascadeType.ALL)
  private final ContactDetails contactDetails;

  @JsonCreator
  public Company(@JsonProperty("id") Long id,
                 @JsonProperty("name") String name,
                 @JsonProperty("taxIdentificationNumber") String taxIdentificationNumber,
                 @JsonProperty("accountNumber") AccountNumber accountNumber,
                 @JsonProperty("contactDetails") ContactDetails contactDetails) {
    this.id = id;
    this.name = name;
    this.taxIdentificationNumber = taxIdentificationNumber;
    this.accountNumber = accountNumber;
    this.contactDetails = contactDetails;
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
    return String.format("id: %d, name: %s, taxIdentificationNumber: %s, accountNumber: %s, contactDetails: %s",
        id, name, taxIdentificationNumber, accountNumber, contactDetails);
  }
}
