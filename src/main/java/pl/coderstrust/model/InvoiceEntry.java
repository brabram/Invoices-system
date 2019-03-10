package pl.coderstrust.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Objects;

@JsonDeserialize(builder = InvoiceEntry.Builder.class)
public final class InvoiceEntry {

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

  protected InvoiceEntry(InvoiceEntry.Builder builder) {
    this.id = builder.id;
    this.item = builder.item;
    this.quantity = builder.quantity;
    this.price = builder.price;
    this.vatValue = builder.vatValue;
    this.grossValue = builder.grossValue;
    this.vatRate = builder.vatRate;
  }

  public static InvoiceEntry.Builder builder() {
    return new InvoiceEntry.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private Long id;
    private String item;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal vatValue;
    private BigDecimal grossValue;
    private Vat vatRate;

    public InvoiceEntry.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public InvoiceEntry.Builder withItem(String item) {
      this.item = item;
      return this;
    }

    public InvoiceEntry.Builder withQuantity(Long quantity) {
      this.quantity = quantity;
      return this;
    }

    public InvoiceEntry.Builder withPrice(BigDecimal price) {
      this.price = price;
      return this;
    }

    public InvoiceEntry.Builder withVatValue(BigDecimal vatValue) {
      this.vatValue = vatValue;
      return this;
    }

    public InvoiceEntry.Builder withGrossValue(BigDecimal grossValue) {
      this.grossValue = grossValue;
      return this;
    }

    public InvoiceEntry.Builder withVatRate(Vat vatRate) {
      this.vatRate = vatRate;
      return this;
    }

    public InvoiceEntry build() {
      return new InvoiceEntry(this);
    }
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
    return "InvoiceEntry{"
        + "id=" + id
        + ", item='" + item + '\''
        + ", quantity=" + quantity
        + ", price=" + price
        + ", vatValue=" + vatValue
        + ", grossValue=" + grossValue
        + ", vatRate=" + vatRate
        + '}';
  }
}
