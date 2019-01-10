package pl.coderstrust.model;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceEntry {
  private String id;
  private String item;
  private Long quantity;
  private BigDecimal price;
  private BigDecimal vatValue;
  private BigDecimal grossValue;
  private Vat vatRate;

  public InvoiceEntry(String id, String item, Long quantity, BigDecimal price, BigDecimal vatValue, BigDecimal grossValue, Vat vatRate) {
    this.id = id;
    this.item = item;
    this.quantity = quantity;
    this.price = price;
    this.vatValue = vatValue;
    this.grossValue = grossValue;
    this.vatRate = vatRate;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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
    return Objects.equals(id, invoiceEntry.id) &&
        Objects.equals(item, invoiceEntry.item) &&
        Objects.equals(quantity, invoiceEntry.quantity) &&
        Objects.equals(price, invoiceEntry.price) &&
        Objects.equals(vatValue, invoiceEntry.vatValue) &&
        Objects.equals(grossValue, invoiceEntry.grossValue) &&
        vatRate == invoiceEntry.vatRate;
  }

    @Override
    public int hashCode() {
        return Objects.hash(id, item, quantity, price, vatValue, grossValue, vatRate);
    }

  @Override
  public String toString() {
    return String.format("id: %s, item: %s, quantity: %s, price: %s, vatValue: %s, grossValue: %s, vatRate: %s",
        id, item, quantity, price, vatValue, grossValue, vatRate);
  }
}
