package pl.coderstrust.database.sql.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class SqlAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private final Long id;
  private final String street;
  private final String number;
  private final String postalCode;
  private final String city;
  private final String country;

  private SqlAddress() {
    this.id = null;
    this.street = null;
    this.number = null;
    this.postalCode = null;
    this.city = null;
    this.country = null;
  }

  protected SqlAddress(SqlAddress.Builder builder) {
    this.id = builder.id;
    this.street = builder.street;
    this.number = builder.number;
    this.postalCode = builder.postalCode;
    this.city = builder.city;
    this.country = builder.country;
  }

  public static SqlAddress.Builder builder() {
    return new SqlAddress.Builder();
  }

  public static class Builder {

    private Long id;
    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;

    public SqlAddress.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public SqlAddress.Builder withStreet(String street) {
      this.street = street;
      return this;
    }

    public SqlAddress.Builder withNumber(String number) {
      this.number = number;
      return this;
    }

    public SqlAddress.Builder withPostalCode(String postalCode) {
      this.postalCode = postalCode;
      return this;
    }

    public SqlAddress.Builder withCity(String city) {
      this.city = city;
      return this;
    }

    public SqlAddress.Builder withCountry(String country) {
      this.country = country;
      return this;
    }

    public SqlAddress build() {
      return new SqlAddress( this );
    }
  }

  public Long getId() {
    return id;
  }

  public String getStreet() {
    return street;
  }

  public String getNumber() {
    return number;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SqlAddress)) {
      return false;
    }
    SqlAddress sqlAddress = (SqlAddress) o;
    return Objects.equals(id, sqlAddress.id)
        && Objects.equals(street, sqlAddress.street)
        && Objects.equals(number, sqlAddress.number)
        && Objects.equals(postalCode, sqlAddress.postalCode)
        && Objects.equals(city, sqlAddress.city)
        && Objects.equals(country, sqlAddress.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, street, number, postalCode, city, country);
  }

  @Override
  public String toString() {
    return "SqlAddress{" +
            "id=" + id +
            ", street='" + street + '\'' +
            ", number='" + number + '\'' +
            ", postalCode='" + postalCode + '\'' +
            ", city='" + city + '\'' +
            ", country='" + country + '\'' +
            '}';
  }
}
