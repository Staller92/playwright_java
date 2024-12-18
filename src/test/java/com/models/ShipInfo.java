package com.models;

import java.util.Objects;

public class ShipInfo {
    private String firstName;
    private String lastName;
    private String zip;

    private ShipInfo() {
    }

    private ShipInfo(String firstName, String lastName, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zip = zip;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "ShipInfo{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipInfo shipInfo = (ShipInfo) o;
        return firstName.equals(shipInfo.firstName) && lastName.equals(shipInfo.lastName) && zip.equals(shipInfo.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, zip);
    }

    public static Builder builder() {
        return new Builder();
    }

    // Builder pattern
    public static class Builder {
        private String firstName;
        private String lastName;
        private String zip;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder zip(String zip) {
            this.zip = zip;
            return this;
        }

        public ShipInfo build() {
            return new ShipInfo(firstName, lastName, zip);
        }
    }
}
