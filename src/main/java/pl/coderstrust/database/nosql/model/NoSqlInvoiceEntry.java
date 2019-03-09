package pl.coderstrust.database.nosql.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.math.BigDecimal;
import java.util.Objects;
import org.springframework.data.annotation.PersistenceConstructor;

@JsonDeserialize(builder = NoSqlInvoiceEntry.Builder.class)
public class NoSqlInvoiceEntry {

  private final String item;
  private final Long quantity;
  private final BigDecimal price;
  private final BigDecimal vatValue;
  private final BigDecimal grossValue;
  private final NoSqlVat vatRate;

  @PersistenceConstructor
  private NoSqlInvoiceEntry(String item, Long quantity, BigDecimal price, BigDecimal vatValue, BigDecimal grossValue, NoSqlVat vatRate) {
    this.item = item;
    this.quantity = quantity;
    this.price = price;
    this.vatValue = vatValue;
    this.grossValue = grossValue;
    this.vatRate = vatRate;
  }

  protected NoSqlInvoiceEntry(NoSqlInvoiceEntry.Builder builder) {
    this.item = builder.item;
    this.quantity = builder.quantity;
    this.price = builder.price;
    this.vatValue = builder.vatValue;
    this.grossValue = builder.grossValue;
    this.vatRate = builder.vatRate;
  }

  public static NoSqlInvoiceEntry.Builder builder() {
    return new NoSqlInvoiceEntry.Builder();
  }

  @JsonPOJOBuilder
  public static class Builder {

    private String item;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal vatValue;
    private BigDecimal grossValue;
    private NoSqlVat vatRate;

    public NoSqlInvoiceEntry.Builder withItem(String item) {
      this.item = item;
      return this;
    }

    public NoSqlInvoiceEntry.Builder withQuantity(Long quantity) {
      this.quantity = quantity;
      return this;
    }

    public NoSqlInvoiceEntry.Builder withPrice(BigDecimal price) {
      this.price = price;
      return this;
    }

    public NoSqlInvoiceEntry.Builder withVatValue(BigDecimal vatValue) {
      this.vatValue = vatValue;
      return this;
    }

    public NoSqlInvoiceEntry.Builder withGrossValue(BigDecimal grossValue) {
      this.grossValue = grossValue;
      return this;
    }

    public NoSqlInvoiceEntry.Builder withVatRate(NoSqlVat vatRate) {
      this.vatRate = vatRate;
      return this;
    }

    public NoSqlInvoiceEntry build() {
      return new NoSqlInvoiceEntry(this);
    }
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

  public NoSqlVat getVatRate() {
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
    NoSqlInvoiceEntry noSqlInvoiceEntry = (NoSqlInvoiceEntry) o;
    return Objects.equals(item, noSqlInvoiceEntry.item)
        && Objects.equals(quantity, noSqlInvoiceEntry.quantity)
        && Objects.equals(price, noSqlInvoiceEntry.price)
        && Objects.equals(vatValue, noSqlInvoiceEntry.vatValue)
        && Objects.equals(grossValue, noSqlInvoiceEntry.grossValue)
        && vatRate == noSqlInvoiceEntry.vatRate;
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, quantity, price, vatValue, grossValue, vatRate);
  }

  @Override
  public String toString() {
    return "NoSqlInvoiceEntry{"
        + "item='" + item + '\''
        + ", quantity=" + quantity
        + ", price=" + price
        + ", vatValue=" + vatValue
        + ", grossValue=" + grossValue
        + ", vatRate=" + vatRate
        + '}';
  }
}
