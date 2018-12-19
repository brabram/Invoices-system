package pl.coderstrust.inovices.model;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceEntry {
    private String id;
    private String description;
    private BigDecimal price;
    private BigDecimal vatValue;
    private VAT vatRate;

    public InvoiceEntry(String id, String description, BigDecimal price, BigDecimal vatValue, VAT vatRate) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.vatValue = vatValue;
        this.vatRate = vatRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public VAT getVatRate() {
        return vatRate;
    }

    public void setVatRate(VAT vatRate) {
        this.vatRate = vatRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceEntry that = (InvoiceEntry) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(vatValue, that.vatValue) &&
                vatRate == that.vatRate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, vatValue, vatRate);
    }

    @Override
    public String toString() {
        return "InvoiceEntry{"
                + "id='" + id + '\''
                + ", description='" + description + '\''
                + ", price=" + price
                + ", vatValue=" + vatValue
                + ", vatRate=" + vatRate
                + '}';
    }
}
