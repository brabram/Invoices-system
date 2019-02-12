package pl.coderstrust.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class InvoiceEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "The id of invoice entry.", dataType = "Long", position = -1)
  private Long id;

  @ApiModelProperty(value = "Name of the item", example = "10w40 Castrol engine oil")
  private String item;

  @ApiModelProperty(value = "Total quantity of items", example = "10")
  private Long quantity;

  @ApiModelProperty(value = "Price of single item without VAT value, only digits and dot ('.') as a separator acceptable", example = "100.00")
  private BigDecimal price;

  @ApiModelProperty(value = "Value of VAT, only digits and dot ('.') as a separator acceptable", example = "23.00")
  private BigDecimal vatValue;

  @ApiModelProperty(value = "Value of all items with VAT value, only digits and dot ('.') as a separator acceptable", example = "1230.00")
  private BigDecimal grossValue;

  @ApiModelProperty(value = "Tax amount", example = "VAT_23")
  private Vat vatRate;

  protected InvoiceEntry() {
  }

  public InvoiceEntry(Long id, String item, Long quantity, BigDecimal price, BigDecimal vatValue, BigDecimal grossValue, Vat vatRate) {
    this.id = id;
    this.item = item;
    this.quantity = quantity;
    this.price = price;
    this.vatValue = vatValue;
    this.grossValue = grossValue;
    this.vatRate = vatRate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getVatValue() {
    return vatValue;
  }

  public void setVatValue(BigDecimal vatValue) {
    this.vatValue = vatValue;
  }

  public BigDecimal getGrossValue() {
    return grossValue;
  }

  public void setGrossValue(BigDecimal grossValue) {
    this.grossValue = grossValue;
  }

  public Vat getVatRate() {
    return vatRate;
  }

  public void setVatRate(Vat vatRate) {
    this.vatRate = vatRate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InvoiceEntry invoiceEntry = (InvoiceEntry) o;
    return Objects.equals(id, invoiceEntry.id)
        && Objects.equals(item, invoiceEntry.item)
        && Objects.equals(quantity, invoiceEntry.quantity)
        && Objects.equals(price, invoiceEntry.price)
        && Objects.equals(vatValue, invoiceEntry.vatValue)
        && Objects.equals(grossValue, invoiceEntry.grossValue)
        && vatRate == invoiceEntry.vatRate;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, item, quantity, price, vatValue, grossValue, vatRate);
  }

  @Override
  public String toString() {
    return String.format("id: %d, item: %s, quantity: %s, price: %s, vatValue: %s, grossValue: %s, vatRate: %s",
        id, item, quantity, price, vatValue, grossValue, vatRate);
  }
}
