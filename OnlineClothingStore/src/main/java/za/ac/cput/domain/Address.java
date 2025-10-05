package za.ac.cput.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "address_id")
    private String addressId;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected Address() {}

    private Address(Builder builder) {
        this.addressId = builder.addressId;
        this.street = builder.street;
        this.city = builder.city;
        this.province = builder.province;
        this.postalCode = builder.postalCode;
        this.country = builder.country;
        this.user = builder.user;
    }

    public String getAddressId() { return addressId; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getProvince() { return province; }
    public String getPostalCode() { return postalCode; }
    public String getCountry() { return country; }
    public User getUser() { return user; }

    @Override
    public String toString() {
        return "Address{" +
                "addressId='" + addressId + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public static class Builder {
        private String addressId;
        private String street;
        private String city;
        private String province;
        private String postalCode;
        private String country;
        private User user;

        public Builder setAddressId(String addressId) { this.addressId = addressId; return this; }
        public Builder setStreet(String street) { this.street = street; return this; }
        public Builder setCity(String city) { this.city = city; return this; }
        public Builder setProvince(String province) { this.province = province; return this; }
        public Builder setPostalCode(String postalCode) { this.postalCode = postalCode; return this; }
        public Builder setCountry(String country) { this.country = country; return this; }
        public Builder setUser(User user) { this.user = user; return this; }

        public Address build() { return new Address(this); }
    }
}
