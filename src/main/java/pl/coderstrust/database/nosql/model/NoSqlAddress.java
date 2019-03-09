package pl.coderstrust.database.nosql.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Objects;

@JsonDeserialize(builder = NoSqlAddress.Builder.class)
public class NoSqlAddress {

  private final String street;
  private final String number;
  private final String postalCode;
  private final String city;
  private final String country;

  @PersistenceConstructor
  private NoSqlAddress(String street, String number, String postalCode, String city, String country) {
    this.street = street;
    this.number = number;
    this.postalCode = postalCode;
    this.city = city;
    this.country = country;
  }

  protected NoSqlAddress(NoSqlAddress.Builder builder) {
    this.street = builder.street;
    this.number = builder.number;
    this.postalCode = builder.postalCode;
    this.city = builder.city;
    this.country = builder.country;
  }

  public static NoSqlAddress.Builder builder() {
    return new NoSqlAddress.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;

    public NoSqlAddress.Builder withStreet(String street) {
      this.street = street;
      return this;
    }

    public NoSqlAddress.Builder withNumber(String number) {
      this.number = number;
      return this;
    }

    public NoSqlAddress.Builder withPostalCode(String postalCode) {
      this.postalCode = postalCode;
      return this;
    }

    public NoSqlAddress.Builder withCity(String city) {
      this.city = city;
      return this;
    }

    public NoSqlAddress.Builder withCountry(String country) {
      this.country = country;
      return this;
    }

    public NoSqlAddress build() {
      return new NoSqlAddress( this );
    }
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
    if (!(o instanceof NoSqlAddress)) {
      return false;
    }
    NoSqlAddress noSqlAddress = (NoSqlAddress) o;
    return Objects.equals(street, noSqlAddress.street)
        && Objects.equals(number, noSqlAddress.number)
        && Objects.equals(postalCode, noSqlAddress.postalCode)
        && Objects.equals(city, noSqlAddress.city)
        && Objects.equals(country, noSqlAddress.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(street, number, postalCode, city, country);
  }

  @Override
  public String toString() {
    return "NoSqlAddress{" +
            "street='" + street + '\'' +
            ", number='" + number + '\'' +
            ", postalCode='" + postalCode + '\'' +
            ", city='" + city + '\'' +
            ", country='" + country + '\'' +
            '}';
  }
}
