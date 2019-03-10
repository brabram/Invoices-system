package pl.coderstrust.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@JsonDeserialize(builder = Invoice.Builder.class)
public final class Invoice {

  @ApiModelProperty(value = "The id of invoice.", example = "1234", dataType = "Long", position = -1)
  private final Long id;

  @ApiModelProperty(value = "Invoice number", example = "FV/1234a")
  private final String number;

  @ApiModelProperty(value = "Issue date of invoice", example = "2019-02-04")
  private final LocalDate issueDate;

  @ApiModelProperty(value = "Due date of invoice", example = "2019-02-18")
  private final LocalDate dueDate;

  @ApiModelProperty(value = "Total net value with dot ('.') as a separator", example = "1000.00")
  private final BigDecimal totalNetValue;

  @ApiModelProperty(value = "Total gross value with dot ('.') as a separator", example = "1023.00")
  private final BigDecimal totalGrossValue;

  private final Company seller;

  private final Company buyer;

  private final List<InvoiceEntry> entries;

  protected Invoice(Invoice.Builder builder) {
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

  public static Invoice.Builder builder() {
    return new Invoice.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private Long id;
    private String number;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal totalNetValue;
    private BigDecimal totalGrossValue;
    private Company seller;
    private Company buyer;
    private List<InvoiceEntry> entries;

    public Invoice.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Invoice.Builder withNumber(String number) {
      this.number = number;
      return this;
    }

    public Invoice.Builder withIssueDate(LocalDate issueDate) {
      this.issueDate = issueDate;
      return this;
    }

    public Invoice.Builder withDueDate(LocalDate dueDate) {
      this.dueDate = dueDate;
      return this;
    }

    public Invoice.Builder withTotalNetValue(BigDecimal totalNetValue) {
      this.totalNetValue = totalNetValue;
      return this;
    }

    public Invoice.Builder withTotalGrossValue(BigDecimal totalGrossValue) {
      this.totalGrossValue = totalGrossValue;
      return this;
    }

    public Invoice.Builder withSeller(Company seller) {
      this.seller = seller;
      return this;
    }

    public Invoice.Builder withBuyer(Company buyer) {
      this.buyer = buyer;
      return this;
    }

    public Invoice.Builder withEntries(List<InvoiceEntry> entries) {
      this.entries = entries;
      return this;
    }

    public Invoice build() {
      return new Invoice(this);
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

  public Company getSeller() {
    return seller;
  }

  public Company getBuyer() {
    return buyer;
  }

  public List<InvoiceEntry> getEntries() {
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
    Invoice invoice = (Invoice) o;
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
    return "Invoice{"
        + "id=" + id
        + ", withNumber='" + number + '\''
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
