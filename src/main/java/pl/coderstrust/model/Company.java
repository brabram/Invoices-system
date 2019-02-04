package pl.coderstrust.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ApiModelProperty(value = "company name", example = "Jan Kowalski - Transport & Logistics Services")
  private String name;

  @ApiModelProperty(value = "tax identification number, 10 digits without spaces", example = "2541278654")
  private String taxIdentificationNumber;

  @OneToOne(cascade = CascadeType.ALL)
  private AccountNumber accountNumber;

  @OneToOne(cascade = CascadeType.ALL)
  private ContactDetails contactDetails;

  protected Company() {
  }

  public Company(Long id, String name, String taxIdentificationNumber, AccountNumber accountNumber, ContactDetails contactDetails) {
    this.id = id;
    this.name = name;
    this.taxIdentificationNumber = taxIdentificationNumber;
    this.accountNumber = accountNumber;
    this.contactDetails = contactDetails;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  public void setTaxIdentificationNumber(String taxIdentificationNumber) {
    this.taxIdentificationNumber = taxIdentificationNumber;
  }

  public AccountNumber getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(AccountNumber accountNumber) {
    this.accountNumber = accountNumber;
  }

  public ContactDetails getContactDetails() {
    return contactDetails;
  }

  public void setContactDetails(ContactDetails contactDetails) {
    this.contactDetails = contactDetails;
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
