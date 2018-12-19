package pl.coderstrust.inovices.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Invoice {

    private String id;
    private int number;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private Company seller;
    private Company buyer;
    private List<InvoiceEntry> entires;

    public Invoice(String id, int number, LocalDate issueDate, LocalDate dueDate, Company seller, Company buyer, List<InvoiceEntry> entires) {
        this.id = id;
        this.number = number;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.seller = seller;
        this.buyer = buyer;
        this.entires = entires;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<InvoiceEntry> getEntires() {
        return entires;
    }

    public void setEntires(List<InvoiceEntry> entires) {
        this.entires = entires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return number == invoice.number &&
                Objects.equals(id, invoice.id) &&
                Objects.equals(issueDate, invoice.issueDate) &&
                Objects.equals(dueDate, invoice.dueDate) &&
                Objects.equals(seller, invoice.seller) &&
                Objects.equals(buyer, invoice.buyer) &&
                Objects.equals(entires, invoice.entires);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, issueDate, dueDate, seller, buyer, entires);
    }

    @Override
    public String toString() {
        return "Invoice{"
                + "id='" + id + '\''
                + ", number=" + number
                + ", issueDate=" + issueDate
                + ", dueDate=" + dueDate
                + ", seller=" + seller
                + ", buyer=" + buyer
                + ", entires=" + entires
                + '}';
    }
}
