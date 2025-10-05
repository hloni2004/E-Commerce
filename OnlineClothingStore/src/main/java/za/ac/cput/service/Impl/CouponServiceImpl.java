package za.ac.cput.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Coupon;
import za.ac.cput.repository.CouponRepository;
import za.ac.cput.service.CouponService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository repository;

    @Autowired
    public CouponServiceImpl(CouponRepository repository) {
        this.repository = repository;
    }

    @Override
    public Coupon create(Coupon coupon) {
        return repository.save(coupon);
    }

    @Override
    public Coupon read(String couponId) {
        return repository.findById(couponId).orElse(null);
    }

    @Override
    public Coupon update(Coupon coupon) {
        if (repository.existsById(coupon.getCouponId())) {
            return repository.save(coupon);
        }
        return null;
    }

    @Override
    public void delete(String couponId) {
        repository.deleteById(couponId);
    }

    @Override
    public List<Coupon> getAll() {
        return repository.findAll();
    }

    @Override
    public Coupon getCouponByCode(String code) {
        return repository.findByCode(code).orElse(null);
    }

    @Override
    public List<Coupon> getCouponsByStatus(String status) {
        return repository.findByStatus(status);
    }

    @Override
    public List<Coupon> getCouponsExpiringBefore(LocalDateTime date) {
        return repository.findByExpiryDateBefore(date);
    }

    @Override
    public List<Coupon> getCouponsExpiringAfter(LocalDateTime date) {
        return repository.findByExpiryDateAfter(date);
    }

    @Override
    public List<Coupon> getActiveCouponsNotExpired() {
        return repository.findByStatusAndExpiryDateAfter("ACTIVE", LocalDateTime.now());
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }

    @Override
    public List<Coupon> getCouponsByDiscountRange(double minDiscount, double maxDiscount) {
        return repository.findByDiscountPercentageBetween(minDiscount, maxDiscount);
    }
}