package pl.coderstrust.inovices.model;

import java.util.Objects;

public class Company {
    private String id;
    private String name;
    private String adress;
    private String taxId;
    private int accountNumber;
    private int phoneNumber;
    private String email;

    public Company(String id, String name, String adress, String taxId, int accountNumber, int phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.taxId = taxId;
        this.accountNumber = accountNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return accountNumber == company.accountNumber &&
                phoneNumber == company.phoneNumber &&
                Objects.equals(id, company.id) &&
                Objects.equals(name, company.name) &&
                Objects.equals(adress, company.adress) &&
                Objects.equals(taxId, company.taxId) &&
                Objects.equals(email, company.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, adress, taxId, accountNumber, phoneNumber, email);
    }

    @Override
    public String toString() {
        return "Company{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", adress='" + adress + '\''
                + ", taxId='" + taxId + '\''
                + ", accountNumber=" + accountNumber
                + ", phoneNumber=" + phoneNumber
                + ", email='" + email + '\''
                + '}';
    }
}
