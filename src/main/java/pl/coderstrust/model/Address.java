package pl.coderstrust.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

@JsonDeserialize(builder = Address.Builder.class)
public final class Address {

  @ApiModelProperty(value = "The id of address.", dataType = "Long", position = -1)
  private final Long id;

  @ApiModelProperty(value = "Street name", example = "Warszawska")
  private final String street;

  @ApiModelProperty(value = "Number of house, building", example = "15a/1")
  private final String number;

  @ApiModelProperty(value = "PostalCode without special sign '-'", example = "00810")
  private final String postalCode;

  @ApiModelProperty(value = "City name", example = "Warsaw")
  private final String city;

  @ApiModelProperty(value = "Country name", example = "Poland")
  private final String country;

  protected Address(Address.Builder builder) {
    this.id = builder.id;
    this.street = builder.street;
    this.number = builder.number;
    this.postalCode = builder.postalCode;
    this.city = builder.city;
    this.country = builder.country;
  }

  public static Address.Builder builder() {
    return new Address.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private Long id;
    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;

    public Address.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Address.Builder withStreet(String street) {
      this.street = street;
      return this;
    }

    public Address.Builder withNumber(String number) {
      this.number = number;
      return this;
    }

    public Address.Builder withPostalCode(String postalCode) {
      this.postalCode = postalCode;
      return this;
    }

    public Address.Builder withCity(String city) {
      this.city = city;
      return this;
    }

    public Address.Builder withCountry(String country) {
      this.country = country;
      return this;
    }

    public Address build() {
      return new Address(this);
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
    if (!(o instanceof Address)) {
      return false;
    }
    Address address = (Address) o;
    return Objects.equals(id, address.id)
        && Objects.equals(street, address.street)
        && Objects.equals(number, address.number)
        && Objects.equals(postalCode, address.postalCode)
        && Objects.equals(city, address.city)
        && Objects.equals(country, address.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, street, number, postalCode, city, country);
  }

  @Override
  public String toString() {
    return "Address{"
        + "id=" + id
        + ", street='" + street + '\''
        + ", number='" + number + '\''
        + ", postalCode='" + postalCode + '\''
        + ", city='" + city + '\''
        + ", country='" + country + '\''
        + '}';
  }
}
