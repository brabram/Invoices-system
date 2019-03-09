package pl.coderstrust.database.sql.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class SqlAccountNumber {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private final Long id;
  private final String ibanNumber;
  private final String localNumber;

  private SqlAccountNumber() {
    this.id = null;
    this.ibanNumber = null;
    this.localNumber = null;
  }

  protected SqlAccountNumber(SqlAccountNumber.Builder builder) {
    this.id = builder.id;
    this.ibanNumber = builder.ibanNumber;
    this.localNumber = builder.localNumber;
  }

  public static SqlAccountNumber.Builder builder() {
    return new SqlAccountNumber.Builder();
  }

  public static class Builder {

    private Long id;
    private String ibanNumber;
    private String localNumber;

    public SqlAccountNumber.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public SqlAccountNumber.Builder withIbanNumber(String ibanNumber) {
      this.ibanNumber = ibanNumber;
      return this;
    }

    public SqlAccountNumber.Builder withLocalNumber(String localNumber) {
      this.localNumber = localNumber;
      return this;
    }

    public SqlAccountNumber build() {
      return new SqlAccountNumber( this );
    }
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
    if (!(o instanceof SqlAccountNumber)) {
      return false;
    }
    SqlAccountNumber that = (SqlAccountNumber) o;
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
    return "SqlAccountNumber{" +
            "id=" + id +
            ", ibanNumber='" + ibanNumber + '\'' +
            ", localNumber='" + localNumber + '\'' +
            '}';
  }
}
