package za.ac.cput.service;

import za.ac.cput.domain.Coupon;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponService extends IService {

    Coupon create(Coupon coupon);

    Coupon read(String couponId);

    Coupon update(Coupon coupon);

    void delete(String couponId);

    List<Coupon> getAll();

    Coupon getCouponByCode(String code);

    List<Coupon> getCouponsByStatus(String status);

    List<Coupon> getCouponsExpiringBefore(LocalDateTime date);

    List<Coupon> getCouponsExpiringAfter(LocalDateTime date);

    List<Coupon> getActiveCouponsNotExpired();

    boolean existsByCode(String code);

    List<Coupon> getCouponsByDiscountRange(double minDiscount, double maxDiscount);
}