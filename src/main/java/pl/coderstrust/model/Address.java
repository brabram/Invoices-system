package pl.coderstrust.model;

import java.util.Objects;

public class Address {
    private String street;
    private int number;
    private int postalCode;
    private String city;
    private String country;

    public Address(String street, int number, int postalCode, String city, String country) {
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return number == address.number &&
                postalCode == address.postalCode &&
                Objects.equals(street, address.street) &&
                Objects.equals(city, address.city) &&
                Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, postalCode, city, country);
    }

    @Override
    public String toString() {
        return "Address{"
                + "street='" + street + '\''
                + ", number=" + number
                + ", postalCode=" + postalCode
                + ", city='" + city + '\''
                + ", country='" + country + '\''
                + '}';
    }
}
