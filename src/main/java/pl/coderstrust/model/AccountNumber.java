package pl.coderstrust.model;

import java.util.Objects;

public class AccountNumber {
    private int ibanNumber;
    private int localNumber;

    public AccountNumber(int ibanNumber, int localNumber) {
        this.ibanNumber = ibanNumber;
        this.localNumber = localNumber;
    }

    public int getIbanNumber() {
        return ibanNumber;
    }

    public void setIbanNumber(int ibanNumber) {
        this.ibanNumber = ibanNumber;
    }

    public int getLocalNumber() {
        return localNumber;
    }

    public void setLocalNumber(int localNumber) {
        this.localNumber = localNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountNumber that = (AccountNumber) o;
        return ibanNumber == that.ibanNumber &&
                localNumber == that.localNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ibanNumber, localNumber);
    }

    @Override
    public String toString() {
        return "AccountNumber{"
                + "ibanNumber=" + ibanNumber
                + ", localNumber=" + localNumber
                + '}';
    }
}
