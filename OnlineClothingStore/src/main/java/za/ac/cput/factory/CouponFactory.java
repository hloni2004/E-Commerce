package za.ac.cput.factory;

import za.ac.cput.domain.*;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;

public class CouponFactory {

    public static Coupon buildCoupon(String couponId, String code,
                                     double discountPercentage, LocalDateTime expiryDate, String status) {

        // Validate couponId
        if (!Helper.isNotEmpty(couponId)) {
            throw new IllegalArgumentException("Coupon ID cannot be null or empty");
        }

        // Validate code
        if (!Helper.isNotEmpty(code)) {
            throw new IllegalArgumentException("Coupon code cannot be null or empty");
        }

        // Validate discount percentage
        if (discountPercentage <= 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }

        // Validate expiry date
        if (expiryDate == null) {
            throw new IllegalArgumentException("Expiry date cannot be null");
        }

        // Check if expiry date is in the future
        if (expiryDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Expiry date must be in the future");
        }

        // Validate status
        if (!Helper.isNotEmpty(status)) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        // Validate status value
        if (!status.equals("ACTIVE") && !status.equals("EXPIRED")) {
            throw new IllegalArgumentException("Status must be either ACTIVE or EXPIRED");
        }

        return new Coupon.Builder()
                .setCouponId(couponId)
                .setCode(code)
                .setDiscountPercentage(discountPercentage)
                .setExpiryDate(expiryDate)
                .setStatus(status)
                .build();
    }
}