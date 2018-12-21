package pl.coderstrust.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Invoice {

    private int id;
    private int number;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private Company seller;
    private Company buyer;
    private List<InvoiceEntry> entries;
    private BigDecimal totalNetValue;
    private BigDecimal totalGrossValue;

    public Invoice(int id, int number, LocalDate issueDate, LocalDate dueDate, Company seller, Company buyer, List<InvoiceEntry> entries, BigDecimal totalNetValue, BigDecimal totalGrossValue) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
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
        return id == invoice.id &&
                number == invoice.number &&
                Objects.equals(issueDate, invoice.issueDate) &&
                Objects.equals(dueDate, invoice.dueDate) &&
                Objects.equals(seller, invoice.seller) &&
                Objects.equals(buyer, invoice.buyer) &&
                Objects.equals(entries, invoice.entries) &&
                Objects.equals(totalNetValue, invoice.totalNetValue) &&
                Objects.equals(totalGrossValue, invoice.totalGrossValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, issueDate, dueDate, seller, buyer, entries, totalNetValue, totalGrossValue);
    }

    @Override
    public String toString() {
        return String.format("id: %d, number: %d, issueDate: %s, dueDate: %s, seller: %s, buyer: %s, entries: %s, totalNetValue: %s, totalGrossValue: %s",
                id, number, issueDate, dueDate, seller, buyer, entries, totalNetValue, totalGrossValue);
    }
}
