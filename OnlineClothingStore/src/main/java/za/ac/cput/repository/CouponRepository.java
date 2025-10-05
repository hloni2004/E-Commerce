package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Coupon;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {

    // Find coupon by code
    Optional<Coupon> findByCode(String code);

    // Find all active coupons
    List<Coupon> findByStatus(String status);

    // Find coupons expiring before a certain date
    List<Coupon> findByExpiryDateBefore(LocalDateTime date);

    // Find coupons expiring after a certain date
    List<Coupon> findByExpiryDateAfter(LocalDateTime date);

    // Find active coupons that haven't expired
    List<Coupon> findByStatusAndExpiryDateAfter(String status, LocalDateTime date);

    // Check if coupon code exists
    boolean existsByCode(String code);

    // Find coupons by discount percentage range
    List<Coupon> findByDiscountPercentageBetween(double minDiscount, double maxDiscount);
}