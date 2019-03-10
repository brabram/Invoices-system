package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public final class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  private Address() {
    this.id = null;
    this.street = null;
    this.number = null;
    this.postalCode = null;
    this.city = null;
    this.country = null;
  }

  @JsonCreator
  public Address(@JsonProperty("id") Long id,
                 @JsonProperty("street")String street,
                 @JsonProperty("number")String number,
                 @JsonProperty("postalCode") String postalCode,
                 @JsonProperty("city") String city,
                 @JsonProperty("country") String country) {
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
    return String.format("id: %d, street: %s, number: %s, postalCode: %s, city: %s, country: %s",
        id, street, number, postalCode, city, country);
  }
}
