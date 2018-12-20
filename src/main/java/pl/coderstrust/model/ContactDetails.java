package pl.coderstrust.model;

import java.util.Objects;

public class ContactDetails {
    private String email;
    private int phoneNumber;
    private String website;
    private Address address;

    public ContactDetails(String email, int phoneNumber, String website, Address address) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContactDetails that = (ContactDetails) o;
        return phoneNumber == that.phoneNumber &&
                Objects.equals(email, that.email) &&
                Objects.equals(website, that.website) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phoneNumber, website, address);
    }

    @Override
    public String toString() {
        return "ContactDetails{"
                + "email='" + email + '\''
                + ", phoneNumber=" + phoneNumber
                + ", website='" + website + '\''
                + ", address=" + address
                + '}';
    }
}
