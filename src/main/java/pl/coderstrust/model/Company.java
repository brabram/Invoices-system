package pl.coderstrust.model;

import java.util.Objects;

public class Company {
    private String id;
    private String name;
    private String taxIdentificationNumber;
    private AccountNumber accountNumber;
    private ContactDetails contactDetails;

    public Company(String id, String name, String taxIdentificationNumber, AccountNumber accountNumber, ContactDetails contactDetails) {
        this.id = id;
        this.name = name;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.accountNumber = accountNumber;
        this.contactDetails = contactDetails;
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

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        return Objects.equals(id, company.id) &&
                Objects.equals(name, company.name) &&
                Objects.equals(taxIdentificationNumber, company.taxIdentificationNumber) &&
                Objects.equals(accountNumber, company.accountNumber) &&
                Objects.equals(contactDetails, company.contactDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, taxIdentificationNumber, accountNumber, contactDetails);
    }

    @Override
    public String toString() {
        return String.format("id: %s, name: %s, taxIdentificationNumber: %s, accountNumber: %s, contactDetails: %s",
                id, name, taxIdentificationNumber, accountNumber, contactDetails);
    }
}
