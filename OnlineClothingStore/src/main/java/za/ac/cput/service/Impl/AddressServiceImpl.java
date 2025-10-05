package za.ac.cput.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.User;
import za.ac.cput.repository.AddressRepository;
import za.ac.cput.service.AddressService;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;

    @Autowired
    public AddressServiceImpl(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Address create(Address address) {
        return repository.save(address);
    }

    @Override
    public Address read(String addressId) {
        return repository.findById(addressId).orElse(null);
    }

    @Override
    public Address update(Address address) {
        if (repository.existsById(address.getAddressId())) {
            return repository.save(address);
        }
        return null;
    }

    @Override
    public void delete(String addressId) {
        repository.deleteById(addressId);
    }

    @Override
    public List<Address> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Address> getAddressesByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public List<Address> getAddressesByUserId(String userId) {
        return repository.findByUser_UserId(userId);
    }

    @Override
    public List<Address> getAddressesByCity(String city) {
        return repository.findByCity(city);
    }

    @Override
    public List<Address> getAddressesByProvince(String province) {
        return repository.findByProvince(province);
    }

    @Override
    public List<Address> getAddressesByCountry(String country) {
        return repository.findByCountry(country);
    }

    @Override
    public long countAddressesByUser(User user) {
        return repository.countByUser(user);
    }
}