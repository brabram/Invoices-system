package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public final class ContactDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "The id of contact details.", dataType = "Long", position = -1)
  private final Long id;

  @ApiModelProperty(value = "Email address", example = "poczta@onet.pl")
  private final String email;

  @ApiModelProperty(value = "Phone number, acceptable use of the '+' sign at the beginning", example = "+48786345298")
  private final String phoneNumber;

  @ApiModelProperty(value = "Web site address", example = "www.company.net.eu")
  private final String website;

  @OneToOne(cascade = CascadeType.ALL)
  private final Address address;

  private ContactDetails() {
    this.id = null;
    this.email = null;
    this.phoneNumber = null;
    this.website = null;
    this.address = null;
  }

  @JsonCreator
  public ContactDetails(@JsonProperty("id") Long id,
                        @JsonProperty("email") String email,
                        @JsonProperty("phoneNumber") String phoneNumber,
                        @JsonProperty("website") String website,
                        @JsonProperty("address") Address address) {
    this.id = id;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.website = website;
    this.address = address;
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

  public Address getAddress() {
    return address;
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
    return "ContactDetails{"
        + "id=" + id
        + ", email='" + email + '\''
        + ", phoneNumber='" + phoneNumber + '\''
        + ", website='" + website + '\''
        + ", address=" + address
        + '}';
  }
}
