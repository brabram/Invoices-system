package pl.coderstrust.database.sql.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class SqlInvoiceEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String item;
  private Long quantity;
  private BigDecimal price;
  private BigDecimal vatValue;
  private BigDecimal grossValue;
  private SqlVat vatRate;

  private SqlInvoiceEntry() {
    this.id = null;
    this.item = null;
    this.quantity = null;
    this.price = null;
    this.vatValue = null;
    this.grossValue = null;
    this.vatRate = null;
  }

  protected SqlInvoiceEntry(SqlInvoiceEntry.Builder builder) {
    this.id = builder.id;
    this.item = builder.item;
    this.quantity = builder.quantity;
    this.price = builder.price;
    this.vatValue = builder.vatValue;
    this.grossValue = builder.grossValue;
    this.vatRate = builder.vatRate;
  }

  public static SqlInvoiceEntry.Builder builder() {
    return new SqlInvoiceEntry.Builder();
  }

  public static class Builder {

    private Long id;
    private String item;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal vatValue;
    private BigDecimal grossValue;
    private SqlVat vatRate;

    public SqlInvoiceEntry.Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public SqlInvoiceEntry.Builder withItem(String item) {
      this.item = item;
      return this;
    }

    public SqlInvoiceEntry.Builder withQuantity(Long quantity) {
      this.quantity = quantity;
      return this;
    }

    public SqlInvoiceEntry.Builder withPrice(BigDecimal price) {
      this.price = price;
      return this;
    }

    public SqlInvoiceEntry.Builder withVatValue(BigDecimal vatValue) {
      this.vatValue = vatValue;
      return this;
    }

    public SqlInvoiceEntry.Builder withGrossValue(BigDecimal grossValue) {
      this.grossValue = grossValue;
      return this;
    }

    public SqlInvoiceEntry.Builder withVatRate(SqlVat vatRate) {
      this.vatRate = vatRate;
      return this;
    }

    public SqlInvoiceEntry build() {
      return new SqlInvoiceEntry( this );
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

  public SqlVat getVatRate() {
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
    SqlInvoiceEntry sqlInvoiceEntry = (SqlInvoiceEntry) o;
    return Objects.equals(id, sqlInvoiceEntry.id)
        && Objects.equals(item, sqlInvoiceEntry.item)
        && Objects.equals(quantity, sqlInvoiceEntry.quantity)
        && Objects.equals(price, sqlInvoiceEntry.price)
        && Objects.equals(vatValue, sqlInvoiceEntry.vatValue)
        && Objects.equals(grossValue, sqlInvoiceEntry.grossValue)
        && vatRate == sqlInvoiceEntry.vatRate;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, item, quantity, price, vatValue, grossValue, vatRate);
  }

  @Override
  public String toString() {
    return "SqlInvoiceEntry{" +
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
