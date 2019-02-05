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
public class ContactDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ApiModelProperty(value = "The id of contact details.", position = -1)
  private Long id;

  @ApiModelProperty(value = "Email address", example = "poczta@onet.pl")
  private String email;

  @ApiModelProperty(value = "Phone number, acceptable use of the '+' sign at the beginning", example = "+48786345298")
  private String phoneNumber;

  @ApiModelProperty(value = "Web site address", example = "www.company.net.eu")
  private String website;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  protected ContactDetails() {
  }

  public ContactDetails(Long id, String email, String phoneNumber, String website, Address address) {
    this.id = id;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.website = website;
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    if (!(o instanceof ContactDetails)) {
      return false;
    }
    ContactDetails that = (ContactDetails) o;
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
    return String.format("id: %d, email: %s, phoneNumber: %s, website: %s, address: %s",
        email, phoneNumber, website, address);
  }
}
