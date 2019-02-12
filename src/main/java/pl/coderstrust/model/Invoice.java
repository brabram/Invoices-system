package pl.coderstrust.model;

import io.swagger.annotations.ApiModelProperty;
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
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "The id of invoice.", example = "1234", dataType = "Long", position = -1)
  private Long id;

  @ApiModelProperty(value = "Invoice number", example = "FV/1234a")
  private String number;

  @ApiModelProperty(value = "Issue date of invoice", example = "2019-02-04")
  private LocalDate issueDate;

  @ApiModelProperty(value = "Due date of invoice", example = "2019-02-18")
  private LocalDate dueDate;

  @ApiModelProperty(value = "Total net value with dot ('.') as a separator", example = "1000.00")
  private BigDecimal totalNetValue;

  @ApiModelProperty(value = "Total gross value with dot ('.') as a separator", example = "1023.00")
  private BigDecimal totalGrossValue;

  @ManyToOne(cascade = CascadeType.ALL)
  private Company seller;

  @ManyToOne(cascade = CascadeType.ALL)
  private Company buyer;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<InvoiceEntry> entries;

  protected Invoice() {
  }

  public Invoice(Long id, String number, LocalDate issueDate, LocalDate dueDate, Company seller, Company buyer, List<InvoiceEntry> entries, BigDecimal totalNetValue, BigDecimal totalGrossValue) {
    this.id = id;
    this.number = number;
    this.issueDate = issueDate;
    this.dueDate = dueDate;
    this.seller = seller;
    this.buyer = buyer;
    this.entries = entries;
    this.totalNetValue = totalNetValue;
    this.totalGrossValue = totalGrossValue;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public Company getSeller() {
    return seller;
  }

  public void setSeller(Company seller) {
    this.seller = seller;
  }

  public Company getBuyer() {
    return buyer;
  }

  public void setBuyer(Company buyer) {
    this.buyer = buyer;
  }

  public List<InvoiceEntry> getEntries() {
    return entries;
  }

  public void setEntries(List<InvoiceEntry> entries) {
    this.entries = entries;
  }

  public BigDecimal getTotalNetValue() {
    return totalNetValue;
  }

  public void setTotalNetValue(BigDecimal totalNetValue) {
    this.totalNetValue = totalNetValue;
  }

  public BigDecimal getTotalGrossValue() {
    return totalGrossValue;
  }

  public void setTotalGrossValue(BigDecimal totalGrossValue) {
    this.totalGrossValue = totalGrossValue;
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
    return String.format("id: %s, number: %s, issueDate: %s, dueDate: %s, seller: %s, buyer: %s, entries: %s, totalNetValue: %s, totalGrossValue: %s",
        id, number, issueDate, dueDate, seller, buyer, entries, totalNetValue, totalGrossValue);
  }
}
