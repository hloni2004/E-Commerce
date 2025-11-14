package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Coupon;
import za.ac.cput.service.CouponService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = "*")
public class CouponController {

    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<Coupon> create(@RequestBody Coupon coupon) {
        Coupon created = couponService.create(coupon);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> read(@PathVariable String id) {
        Coupon coupon = couponService.read(id);
        if (coupon != null) {
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> update(@PathVariable String id, @RequestBody Coupon coupon) {
        Coupon updated = couponService.update(coupon);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        couponService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAll() {
        List<Coupon> coupons = couponService.getAll();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Coupon> getCouponByCode(@PathVariable String code) {
        Coupon coupon = couponService.getCouponByCode(code);
        if (coupon != null) {
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Coupon>> getActiveCoupons() {
        List<Coupon> coupons = couponService.getActiveCouponsNotExpired();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/discount/range")
    public ResponseEntity<List<Coupon>> getCouponsByDiscountRange(@RequestParam double min, @RequestParam double max) {
        List<Coupon> coupons = couponService.getCouponsByDiscountRange(min, max);
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/expiring/before")
    public ResponseEntity<List<Coupon>> getExpiringCouponsBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<Coupon> coupons = couponService.getCouponsExpiringBefore(date);
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/expiring/after")
    public ResponseEntity<List<Coupon>> getExpiringCouponsAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<Coupon> coupons = couponService.getCouponsExpiringAfter(date);
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/exists/code/{code}")
    public ResponseEntity<Boolean> existsByCode(@PathVariable String code) {
        boolean exists = couponService.existsByCode(code);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}
