package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public final class InvoiceEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "The id of invoice entry.", dataType = "Long", position = -1)
  private final Long id;

  @ApiModelProperty(value = "Name of the item", example = "10w40 Castrol engine oil")
  private final String item;

  @ApiModelProperty(value = "Total quantity of items", example = "10")
  private final Long quantity;

  @ApiModelProperty(value = "Price of single item without VAT value, only digits and dot ('.') as a separator acceptable", example = "100.00")
  private final BigDecimal price;

  @ApiModelProperty(value = "Value of VAT, only digits and dot ('.') as a separator acceptable", example = "23.00")
  private final BigDecimal vatValue;

  @ApiModelProperty(value = "Value of all items with VAT value, only digits and dot ('.') as a separator acceptable", example = "1230.00")
  private final BigDecimal grossValue;

  @ApiModelProperty(value = "Tax amount", example = "VAT_23")
  private final Vat vatRate;

  private InvoiceEntry() {
    this.id = null;
    this.item = null;
    this.quantity = null;
    this.price = null;
    this.vatValue = null;
    this.grossValue = null;
    this.vatRate = null;
  }

  @JsonCreator
  public InvoiceEntry(@JsonProperty("id") Long id,
                      @JsonProperty("item") String item,
                      @JsonProperty("quantity") Long quantity,
                      @JsonProperty("price") BigDecimal price,
                      @JsonProperty("vatValue") BigDecimal vatValue,
                      @JsonProperty("grossValue") BigDecimal grossValue,
                      @JsonProperty("vatRate") Vat vatRate) {
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

  public String getItem() {
    return item;
  }

  public Long getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVatValue() {
    return vatValue;
  }

  public BigDecimal getGrossValue() {
    return grossValue;
  }

  public Vat getVatRate() {
    return vatRate;
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
    return "InvoiceEntry{" +
            "id=" + id +
            ", item='" + item + '\'' +
            ", quantity=" + quantity +
            ", price=" + price +
            ", vatValue=" + vatValue +
            ", grossValue=" + grossValue +
            ", vatRate=" + vatRate +
            '}';
  }
}
