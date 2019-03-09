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
public final class AccountNumber {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "The id of account number.", position = -1)
  private final Long id;

  @ApiModelProperty(value = "26 digit account number with country code", example = "PL19200000000120067894552302")
  private final String ibanNumber;

  @ApiModelProperty(value = "26 digit account number", example = "'19200000000120067894552302'")
  private final String localNumber;

  @JsonCreator
  public AccountNumber(@JsonProperty("id") Long id,
                       @JsonProperty("ibanNumber") String ibanNumber,
                       @JsonProperty("localNumber") String localNumber) {
    this.id = id;
    this.ibanNumber = ibanNumber;
    this.localNumber = localNumber;
  }

  public Long getId() {
    return id;
  }

  public String getIbanNumber() {
    return ibanNumber;
  }

  public String getLocalNumber() {
    return localNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AccountNumber)) {
      return false;
    }
    AccountNumber that = (AccountNumber) o;
    return Objects.equals(id, that.id)
        && Objects.equals(ibanNumber, that.ibanNumber)
        && Objects.equals(localNumber, that.localNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ibanNumber, localNumber);
  }

  @Override
  public String toString() {
    return String.format("id: %d, ibanNumber: %s, localNumber: %s", id, ibanNumber, localNumber);
  }
}
