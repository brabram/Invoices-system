package pl.coderstrust.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "The id of address.", dataType = "Long", position = -1)
  private Long id;

  @ApiModelProperty(value = "Street name", example = "Warszawska")
  private String street;

  @ApiModelProperty(value = "Number of house, building", example = "15a/1")
  private String number;

  @ApiModelProperty(value = "PostalCode without special sign '-'", example = "00810")
  private String postalCode;

  @ApiModelProperty(value = "City name", example = "Warsaw")
  private String city;

  @ApiModelProperty(value = "Country name", example = "Poland")
  private String country;

  protected Address() {
  }

  public Address(Long id, String street, String number, String postalCode, String city, String country) {
    this.id = id;
    this.street = street;
    this.number = number;
    this.postalCode = postalCode;
    this.city = city;
    this.country = country;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
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
    return String.format("id: %d, street: %s, number: %s, postalCode: %s, city: %s, country: %s",
        street, number, postalCode, city, country);
  }
}
