package pl.coderstrust.database.sql.model;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class SqlCompany {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private final Long id;
  private final String name;
  private final String taxIdentificationNumber;

  @OneToOne(cascade = CascadeType.ALL)
  private final SqlAccountNumber accountNumber;

  @OneToOne(cascade = CascadeType.ALL)
  private final SqlContactDetails contactDetails;

  private SqlCompany() {
    this.id = null;
    this.name = null;
    this.taxIdentificationNumber = null;
    this.accountNumber = null;
    this.contactDetails = null;
  }

  protected SqlCompany(SqlCompany.Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.taxIdentificationNumber = builder.taxIdentificationNumber;
    this.accountNumber = builder.accountNumber;
    this.contactDetails = builder.contactDetails;
  }

  public static SqlCompany.Builder builder() {
    return new SqlCompany.Builder();
  }

  public static class Builder {

    private Long id;
    private String name;
    private String taxIdentificationNumber;
    private SqlAccountNumber accountNumber;
    private SqlContactDetails contactDetails;

    public SqlCompany.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public SqlCompany.Builder withName(String name) {
      this.name = name;
      return this;
    }

    public SqlCompany.Builder withTaxIdentificationNumber(String taxIdentificationNumber) {
      this.taxIdentificationNumber = taxIdentificationNumber;
      return this;
    }

    public SqlCompany.Builder withAccountNumber(SqlAccountNumber accountNumber) {
      this.accountNumber = accountNumber;
      return this;
    }

    public SqlCompany.Builder withContactDetails(SqlContactDetails contactDetails) {
      this.contactDetails = contactDetails;
      return this;
    }

    public SqlCompany build() {
      return new SqlCompany(this);
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

  public SqlAccountNumber getAccountNumber() {
    return accountNumber;
  }

  public SqlContactDetails getContactDetails() {
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
    SqlCompany sqlCompany = (SqlCompany) o;
    return Objects.equals(id, sqlCompany.id)
        && Objects.equals(name, sqlCompany.name)
        && Objects.equals(taxIdentificationNumber, sqlCompany.taxIdentificationNumber)
        && Objects.equals(accountNumber, sqlCompany.accountNumber)
        && Objects.equals(contactDetails, sqlCompany.contactDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, taxIdentificationNumber, accountNumber, contactDetails);
  }

  @Override
  public String toString() {
    return "SqlCompany{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", taxIdentificationNumber='" + taxIdentificationNumber + '\''
        + ", accountNumber=" + accountNumber
        + ", contactDetails=" + contactDetails
        + '}';
  }
}
