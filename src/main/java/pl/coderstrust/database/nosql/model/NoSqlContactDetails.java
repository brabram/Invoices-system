package pl.coderstrust.database.nosql.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Objects;
import org.springframework.data.annotation.PersistenceConstructor;

@JsonDeserialize(builder = NoSqlContactDetails.Builder.class)
public class NoSqlContactDetails {

  private final String email;
  private final String phoneNumber;
  private final String website;
  private final NoSqlAddress address;

  @PersistenceConstructor
  private NoSqlContactDetails(String email, String phoneNumber, String website, NoSqlAddress address) {
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.website = website;
    this.address = address;
  }

  protected NoSqlContactDetails(NoSqlContactDetails.Builder builder) {
    this.email = builder.email;
    this.phoneNumber = builder.phoneNumber;
    this.website = builder.website;
    this.address = builder.address;
  }

  public static NoSqlContactDetails.Builder builder() {
    return new NoSqlContactDetails.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private String email;
    private String phoneNumber;
    private String website;
    private NoSqlAddress address;

    public NoSqlContactDetails.Builder withEmail(String email) {
      this.email = email;
      return this;
    }

    public NoSqlContactDetails.Builder withPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public NoSqlContactDetails.Builder withWebsite(String website) {
      this.website = website;
      return this;
    }

    public NoSqlContactDetails.Builder withAddress(NoSqlAddress address) {
      this.address = address;
      return this;
    }

    public NoSqlContactDetails build() {
      return new NoSqlContactDetails(this);
    }
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

  public NoSqlAddress getAddress() {
    return address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof NoSqlContactDetails)) {
      return false;
    }
    NoSqlContactDetails that = (NoSqlContactDetails) o;
    return Objects.equals(email, that.email)
        && Objects.equals(phoneNumber, that.phoneNumber)
        && Objects.equals(website, that.website)
        && Objects.equals(address, that.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, phoneNumber, website, address);
  }

  @Override
  public String toString() {
    return "NoSqlContactDetails{"
        + "email='" + email + '\''
        + ", phoneNumber='" + phoneNumber + '\''
        + ", website='" + website + '\''
        + ", address=" + address
        + '}';
  }
}
