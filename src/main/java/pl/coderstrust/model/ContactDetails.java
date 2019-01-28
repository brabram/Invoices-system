package pl.coderstrust.model;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;


@Entity
public class ContactDetails {
  private String email;
  private String phoneNumber;
  private String website;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  protected ContactDetails() {
  }

  public ContactDetails(String email, String phoneNumber, String website, Address address) {
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.website = website;
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContactDetails contactDetails = (ContactDetails) o;
    return phoneNumber.equals(contactDetails.phoneNumber)
        && Objects.equals(email, contactDetails.email)
        && Objects.equals(website, contactDetails.website)
        && Objects.equals(address, contactDetails.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, phoneNumber, website, address);
  }

  @Override
  public String toString() {
    return String.format("email: %s, phoneNumber: %s, website: %s, address: %s",
        email, phoneNumber, website, address);
  }
}
