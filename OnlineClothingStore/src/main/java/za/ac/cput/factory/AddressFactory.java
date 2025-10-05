package za.ac.cput.factory;

import za.ac.cput.domain.Address;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

public class AddressFactory {

    public static Address buildAddress(String addressId, String street, String city,
                                       String province, String postalCode, String country, User user) {

        // Validate addressId
        if (!Helper.isNotEmpty(addressId)) {
            throw new IllegalArgumentException("Address ID cannot be null or empty");
        }

        // Validate street
        if (!Helper.isNotEmpty(street)) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }

        // Validate city
        if (!Helper.isNotEmpty(city)) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }

        // Validate province
        if (!Helper.isNotEmpty(province)) {
            throw new IllegalArgumentException("Province cannot be null or empty");
        }

        // Validate postal code
        if (!Helper.isNotEmpty(postalCode)) {
            throw new IllegalArgumentException("Postal code cannot be null or empty");
        }

        // Validate country
        if (!Helper.isNotEmpty(country)) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }

        // Validate user
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        return new Address.Builder()
                .setAddressId(addressId)
                .setStreet(street)
                .setCity(city)
                .setProvince(province)
                .setPostalCode(postalCode)
                .setCountry(country)
                .setUser(user)
                .build();
    }
}