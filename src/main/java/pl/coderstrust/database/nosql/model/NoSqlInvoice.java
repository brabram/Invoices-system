package pl.coderstrust.database.nosql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Document
@JsonDeserialize(builder = NoSqlInvoice.Builder.class)
public class NoSqlInvoice {

  @Id
  @JsonIgnore
  private String mongoId;

  @Indexed(unique = true)
  private final Long id;
  private final String number;
  private final LocalDate issueDate;
  private final LocalDate dueDate;
  private final BigDecimal totalNetValue;
  private final BigDecimal totalGrossValue;
  private final NoSqlCompany seller;
  private final NoSqlCompany buyer;
  private final List<NoSqlInvoiceEntry> entries;

  @PersistenceConstructor
  private NoSqlInvoice(String mongoId, Long id, String number, LocalDate issueDate, LocalDate dueDate, BigDecimal totalNetValue, BigDecimal totalGrossValue, NoSqlCompany seller, NoSqlCompany buyer, List<NoSqlInvoiceEntry> entries) {
    this.mongoId = mongoId;
    this.id = id;
    this.number = number;
    this.issueDate = issueDate;
    this.dueDate = dueDate;
    this.totalNetValue = totalNetValue;
    this.totalGrossValue = totalGrossValue;
    this.seller = seller;
    this.buyer = buyer;
    this.entries = entries;
  }

  protected NoSqlInvoice(NoSqlInvoice.Builder builder) {
    this.mongoId = builder.mongoId;
    this.id = builder.id;
    this.number = builder.number;
    this.issueDate = builder.issueDate;
    this.dueDate = builder.dueDate;
    this.totalNetValue = builder.totalNetValue;
    this.totalGrossValue = builder.totalGrossValue;
    this.seller = builder.seller;
    this.buyer = builder.buyer;
    this.entries = builder.entries;
  }

  public static NoSqlInvoice.Builder builder() {
    return new NoSqlInvoice.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private String mongoId;
    private Long id;
    private String number;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal totalNetValue;
    private BigDecimal totalGrossValue;
    private NoSqlCompany seller;
    private NoSqlCompany buyer;
    private List<NoSqlInvoiceEntry> entries;


    public NoSqlInvoice.Builder withMongoId(String mongoId) {
      this.mongoId = mongoId;
      return this;
    }

    public NoSqlInvoice.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public NoSqlInvoice.Builder withNumber(String number) {
      this.number = number;
      return this;
    }

    public NoSqlInvoice.Builder withIssueDate(LocalDate issueDate) {
      this.issueDate = issueDate;
      return this;
    }

    public NoSqlInvoice.Builder withDueDate(LocalDate dueDate) {
      this.dueDate = dueDate;
      return this;
    }

    public NoSqlInvoice.Builder withTotalNetValue(BigDecimal totalNetValue) {
      this.totalNetValue = totalNetValue;
      return this;
    }

    public NoSqlInvoice.Builder withTotalGrossValue(BigDecimal totalGrossValue) {
      this.totalGrossValue = totalGrossValue;
      return this;
    }

    public NoSqlInvoice.Builder withSeller(NoSqlCompany seller) {
      this.seller = seller;
      return this;
    }

    public NoSqlInvoice.Builder withBuyer(NoSqlCompany buyer) {
      this.buyer = buyer;
      return this;
    }

    public NoSqlInvoice.Builder withEntries(List<NoSqlInvoiceEntry> entries) {
      this.entries = entries;
      return this;
    }

    public NoSqlInvoice build() {
      return new NoSqlInvoice( this );
    }
  }
 
  public String getMongoId() {
    return mongoId;
  }

  public Long getId() {
    return id;
  }

  public String getNumber() {
    return number;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public NoSqlCompany getSeller() {
    return seller;
  }

  public NoSqlCompany getBuyer() {
    return buyer;
  }

  public List<NoSqlInvoiceEntry> getEntries() {
    return entries;
  }

  public BigDecimal getTotalNetValue() {
    return totalNetValue;
  }

  public BigDecimal getTotalGrossValue() {
    return totalGrossValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NoSqlInvoice noSqlInvoice = (NoSqlInvoice) o;
    return Objects.equals(id, noSqlInvoice.id)
        && Objects.equals(number, noSqlInvoice.number)
        && Objects.equals(issueDate, noSqlInvoice.issueDate)
        && Objects.equals(dueDate, noSqlInvoice.dueDate)
        && Objects.equals(seller, noSqlInvoice.seller)
        && Objects.equals(buyer, noSqlInvoice.buyer)
        && Objects.equals(entries, noSqlInvoice.entries)
        && Objects.equals(totalNetValue, noSqlInvoice.totalNetValue)
        && Objects.equals(totalGrossValue, noSqlInvoice.totalGrossValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, number, issueDate, dueDate, seller, buyer, entries, totalNetValue, totalGrossValue);
  }

  @Override
  public String toString() {
    return "NoSqlInvoice{" +
            "mongoId='" + mongoId + '\'' +
            ", id=" + id +
            ", withNumber='" + number + '\'' +
            ", issueDate=" + issueDate +
            ", dueDate=" + dueDate +
            ", totalNetValue=" + totalNetValue +
            ", totalGrossValue=" + totalGrossValue +
            ", seller=" + seller +
            ", buyer=" + buyer +
            ", entries=" + entries +
            '}';
  }
}
