package pl.coderstrust.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String street;
  private String number;
  private String postalCode;
  private String city;
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
    return Objects.equals(id, address.id) &&
        Objects.equals(street, address.street) &&
        Objects.equals(number, address.number) &&
        Objects.equals(postalCode, address.postalCode) &&
        Objects.equals(city, address.city) &&
        Objects.equals(country, address.country);
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
