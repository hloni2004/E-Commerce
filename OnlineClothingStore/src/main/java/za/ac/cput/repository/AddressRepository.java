package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

    // Find all addresses for a specific user
    List<Address> findByUser(User user);

    // Find addresses by user ID
    List<Address> findByUser_UserId(String userId);

    // Find addresses by city
    List<Address> findByCity(String city);

    // Find addresses by province
    List<Address> findByProvince(String province);

    // Find addresses by country
    List<Address> findByCountry(String country);

    // Find addresses by postal code
    List<Address> findByPostalCode(String postalCode);

    // Find addresses by city and province
    List<Address> findByCityAndProvince(String city, String province);

    // Find a specific address for a user by street
    Optional<Address> findByUserAndStreet(User user, String street);

    // Count addresses for a user
    long countByUser(User user);
}