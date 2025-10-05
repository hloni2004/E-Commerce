package za.ac.cput.service;

import za.ac.cput.domain.Address;
import za.ac.cput.domain.User;

import java.util.List;

public interface AddressService extends IService {

    Address create(Address address);

    Address read(String addressId);

    Address update(Address address);

    void delete(String addressId);

    List<Address> getAll();

    List<Address> getAddressesByUser(User user);

    List<Address> getAddressesByUserId(String userId);

    List<Address> getAddressesByCity(String city);

    List<Address> getAddressesByProvince(String province);

    List<Address> getAddressesByCountry(String country);

    long countAddressesByUser(User user);
}