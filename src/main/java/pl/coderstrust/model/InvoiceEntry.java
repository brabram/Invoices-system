package pl.coderstrust.model;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceEntry {
    private int id;
    private String item;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal vatValue;
    private BigDecimal grossValue;
    private VAT vatRate;

    public InvoiceEntry(int id, String item, Long quantity, BigDecimal price, BigDecimal vatValue, BigDecimal grossValue, VAT vatRate) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.vatValue = vatValue;
        this.grossValue = grossValue;
        this.vatRate = vatRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public VAT getVatRate() {
        return vatRate;
    }

    public void setVatRate(VAT vatRate) {
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
        InvoiceEntry that = (InvoiceEntry) o;
        return id == that.id &&
                Objects.equals(item, that.item) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(price, that.price) &&
                Objects.equals(vatValue, that.vatValue) &&
                Objects.equals(grossValue, that.grossValue) &&
                vatRate == that.vatRate;
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
