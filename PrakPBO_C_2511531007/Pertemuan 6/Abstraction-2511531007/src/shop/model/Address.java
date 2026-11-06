package shop.model;

public final class Address {
    private final String street, city, postalCode;

    public Address(String street, String city, String postalCode) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("city must be not blank");
        }

        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("street must be not blank");
        }

        if (postalCode == null || postalCode.isBlank()) {
            throw new IllegalArgumentException("postalCode must be not blank");
        }
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }
    public String getCity() { return city; }
    public String getStreet() { return street; }
    public String getPostalCode() { return postalCode; }

    @Override
    public String toString() {
        return street + ", " + city + " " + postalCode;
    }
}
