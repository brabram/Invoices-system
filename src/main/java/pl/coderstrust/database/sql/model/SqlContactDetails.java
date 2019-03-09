package pl.coderstrust.database.sql.model;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class SqlContactDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String phoneNumber;
  private String website;

  @OneToOne(cascade = CascadeType.ALL)
  private SqlAddress address;

  private SqlContactDetails() {
    this.id = null;
    this.email = null;
    this.phoneNumber = null;
    this.website = null;
    this.address = null;
  }

  protected SqlContactDetails(SqlContactDetails.Builder builder) {
    this.id = builder.id;
    this.email = builder.email;
    this.phoneNumber = builder.phoneNumber;
    this.website = builder.website;
    this.address = builder.address;
  }

  public static SqlContactDetails.Builder builder() {
    return new SqlContactDetails.Builder();
  }

  public static class Builder {

    private Long id;
    private String email;
    private String phoneNumber;
    private String website;
    private SqlAddress address;

    public SqlContactDetails.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public SqlContactDetails.Builder withEmail(String email) {
      this.email = email;
      return this;
    }

    public SqlContactDetails.Builder withPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public SqlContactDetails.Builder withWebsite(String website) {
      this.website = website;
      return this;
    }

    public SqlContactDetails.Builder withAddress(SqlAddress address) {
      this.address = address;
      return this;
    }

    public SqlContactDetails build() {
      return new SqlContactDetails(this);
    }
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getWebsite() {
    return website;
  }

  public SqlAddress getAddress() {
    return address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SqlContactDetails)) {
      return false;
    }
    SqlContactDetails that = (SqlContactDetails) o;
    return Objects.equals(id, that.id)
        && Objects.equals(email, that.email)
        && Objects.equals(phoneNumber, that.phoneNumber)
        && Objects.equals(website, that.website)
        && Objects.equals(address, that.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, phoneNumber, website, address);
  }

  @Override
  public String toString() {
    return "SqlContactDetails{"
        + "id=" + id
        + ", email='" + email + '\''
        + ", phoneNumber='" + phoneNumber + '\''
        + ", website='" + website + '\''
        + ", address=" + address
        + '}';
  }
}
