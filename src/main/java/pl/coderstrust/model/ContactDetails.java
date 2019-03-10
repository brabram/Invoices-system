package pl.coderstrust.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

@JsonDeserialize(builder = ContactDetails.Builder.class)
public final class ContactDetails {

  @ApiModelProperty(value = "The id of contact details.", dataType = "Long", position = -1)
  private final Long id;

  @ApiModelProperty(value = "Email address", example = "poczta@onet.pl")
  private final String email;

  @ApiModelProperty(value = "Phone number, acceptable use of the '+' sign at the beginning", example = "+48786345298")
  private final String phoneNumber;

  @ApiModelProperty(value = "Web site address", example = "www.company.net.eu")
  private final String website;

  private final Address address;

  protected ContactDetails(ContactDetails.Builder builder) {
    this.id = builder.id;
    this.email = builder.email;
    this.phoneNumber = builder.phoneNumber;
    this.website = builder.website;
    this.address = builder.address;
  }

  public static ContactDetails.Builder builder() {
    return new ContactDetails.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private Long id;
    private String email;
    private String phoneNumber;
    private String website;
    private Address address;

    public ContactDetails.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public ContactDetails.Builder withEmail(String email) {
      this.email = email;
      return this;
    }

    public ContactDetails.Builder withPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public ContactDetails.Builder withWebsite(String website) {
      this.website = website;
      return this;
    }

    public ContactDetails.Builder withAddress(Address address) {
      this.address = address;
      return this;
    }

    public ContactDetails build() {
      return new ContactDetails(this);
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
