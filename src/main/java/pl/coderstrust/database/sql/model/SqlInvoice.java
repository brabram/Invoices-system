package pl.coderstrust.database.sql.model;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class SqlInvoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private final Long id;
  private final String number;
  private final LocalDate issueDate;
  private final LocalDate dueDate;
  private final BigDecimal totalNetValue;
  private final BigDecimal totalGrossValue;

  @ManyToOne(cascade = CascadeType.ALL)
  private SqlCompany seller;

  @ManyToOne(cascade = CascadeType.ALL)
  private SqlCompany buyer;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<SqlInvoiceEntry> entries;

  private SqlInvoice() {
    this.id = null;
    this.number = null;
    this.issueDate = null;
    this.dueDate = null;
    this.totalNetValue = null;
    this.totalGrossValue = null;
    this.seller = null;
    this.buyer = null;
    this.entries = null;
  }

  protected SqlInvoice(SqlInvoice.Builder builder) {
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

  public static SqlInvoice.Builder builder() {
    return new SqlInvoice.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private Long id;
    private String number;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal totalNetValue;
    private BigDecimal totalGrossValue;
    private SqlCompany seller;
    private SqlCompany buyer;
    private List<SqlInvoiceEntry> entries;

    public SqlInvoice.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public SqlInvoice.Builder withNumber(String number) {
      this.number = number;
      return this;
    }

    public SqlInvoice.Builder withIssueDate(LocalDate issueDate) {
      this.issueDate = issueDate;
      return this;
    }

    public SqlInvoice.Builder withDueDate(LocalDate dueDate) {
      this.dueDate = dueDate;
      return this;
    }

    public SqlInvoice.Builder withTotalNetValue(BigDecimal totalNetValue) {
      this.totalNetValue = totalNetValue;
      return this;
    }

    public SqlInvoice.Builder withTotalGrossValue(BigDecimal totalGrossValue) {
      this.totalGrossValue = totalGrossValue;
      return this;
    }

    public SqlInvoice.Builder withSeller(SqlCompany seller) {
      this.seller = seller;
      return this;
    }

    public SqlInvoice.Builder withBuyer(SqlCompany buyer) {
      this.buyer = buyer;
      return this;
    }

    public SqlInvoice.Builder withEntries(List<SqlInvoiceEntry> entries) {
      this.entries = entries;
      return this;
    }

    public SqlInvoice build() {
      return new SqlInvoice(this);
    }
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

  public SqlCompany getSeller() {
    return seller;
  }

  public SqlCompany getBuyer() {
    return buyer;
  }

  public List<SqlInvoiceEntry> getEntries() {
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
    SqlInvoice invoice = (SqlInvoice) o;
    return Objects.equals(id, invoice.id)
        && Objects.equals(number, invoice.number)
        && Objects.equals(issueDate, invoice.issueDate)
        && Objects.equals(dueDate, invoice.dueDate)
        && Objects.equals(seller, invoice.seller)
        && Objects.equals(buyer, invoice.buyer)
        && Objects.equals(entries, invoice.entries)
        && Objects.equals(totalNetValue, invoice.totalNetValue)
        && Objects.equals(totalGrossValue, invoice.totalGrossValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, number, issueDate, dueDate, seller, buyer, entries, totalNetValue, totalGrossValue);
  }

  @Override
  public String toString() {
    return "SqlInvoice{"
        + "id=" + id
        + ", number='" + number + '\''
        + ", issueDate=" + issueDate
        + ", dueDate=" + dueDate
        + ", totalNetValue=" + totalNetValue
        + ", totalGrossValue=" + totalGrossValue
        + ", seller=" + seller
        + ", buyer=" + buyer
        + ", entries=" + entries
        + '}';
  }
}
