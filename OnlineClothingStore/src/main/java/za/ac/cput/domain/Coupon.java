package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @Column(name = "coupon_id")
    private String couponId;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "discount_percentage")
    private double discountPercentage;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "status")
    private String status; // ACTIVE, EXPIRED

    protected Coupon() {}

    private Coupon(Builder builder) {
        this.couponId = builder.couponId;
        this.code = builder.code;
        this.discountPercentage = builder.discountPercentage;
        this.expiryDate = builder.expiryDate;
        this.status = builder.status;
    }

    public String getCouponId() { return couponId; }
    public String getCode() { return code; }
    public double getDiscountPercentage() { return discountPercentage; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Coupon{" +
                "couponId='" + couponId + '\'' +
                ", code='" + code + '\'' +
                ", discountPercentage=" + discountPercentage +
                '}';
    }

    public static class Builder {
        private String couponId;
        private String code;
        private double discountPercentage;
        private LocalDateTime expiryDate;
        private String status;

        public Builder setCouponId(String couponId) { this.couponId = couponId; return this; }
        public Builder setCode(String code) { this.code = code; return this; }
        public Builder setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; return this; }
        public Builder setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; return this; }
        public Builder setStatus(String status) { this.status = status; return this; }

        public Coupon build() { return new Coupon(this); }
    }
}
