package za.ac.cput.service.Impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Coupon;
import za.ac.cput.factory.CouponFactory;
import za.ac.cput.service.CouponService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CouponServiceImplTest {

    @Autowired
    private CouponService couponService;

    private static Coupon testCoupon;

    @BeforeAll
    static void setUp() {
        testCoupon = CouponFactory.buildCoupon(
                "CPN001",
                "SUMMER2024",
                15.0,
                LocalDateTime.now().plusMonths(3),
                "ACTIVE"
        );
    }

    @Test
    @Order(1)
    void testCreate() {
        Coupon created = couponService.create(testCoupon);
        assertNotNull(created);
        assertEquals(testCoupon.getCouponId(), created.getCouponId());
        assertEquals("SUMMER2024", created.getCode());
        assertEquals(15.0, created.getDiscountPercentage());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void testRead() {
        Coupon read = couponService.read(testCoupon.getCouponId());
        assertNotNull(read);
        assertEquals(testCoupon.getCouponId(), read.getCouponId());
        assertEquals("SUMMER2024", read.getCode());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void testUpdate() {
        Coupon existing = couponService.read(testCoupon.getCouponId());
        Coupon updated = new Coupon.Builder()
                .setCouponId(existing.getCouponId())
                .setCode(existing.getCode())
                .setDiscountPercentage(20.0)
                .setExpiryDate(existing.getExpiryDate())
                .setStatus("ACTIVE")
                .build();

        Coupon result = couponService.update(updated);
        assertNotNull(result);
        assertEquals(20.0, result.getDiscountPercentage());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void testGetCouponByCode() {
        Coupon coupon = couponService.getCouponByCode("SUMMER2024");
        assertNotNull(coupon);
        assertEquals(testCoupon.getCouponId(), coupon.getCouponId());
        System.out.println("Coupon by code: " + coupon);
    }

    @Test
    @Order(5)
    void testGetCouponsByStatus() {
        List<Coupon> coupons = couponService.getCouponsByStatus("ACTIVE");
        assertNotNull(coupons);
        assertFalse(coupons.isEmpty());
        System.out.println("Active coupons: " + coupons.size());
    }

    @Test
    @Order(6)
    void testGetActiveCouponsNotExpired() {
        List<Coupon> coupons = couponService.getActiveCouponsNotExpired();
        assertNotNull(coupons);
        assertFalse(coupons.isEmpty());
        System.out.println("Active non-expired coupons: " + coupons.size());
    }

    @Test
    @Order(7)
    void testExistsByCode() {
        boolean exists = couponService.existsByCode("SUMMER2024");
        assertTrue(exists);
        System.out.println("Coupon exists: " + exists);
    }

    @Test
    @Order(8)
    void testGetCouponsByDiscountRange() {
        List<Coupon> coupons = couponService.getCouponsByDiscountRange(10.0, 25.0);
        assertNotNull(coupons);
        assertFalse(coupons.isEmpty());
        System.out.println("Coupons in range: " + coupons.size());
    }

    @Test
    @Order(9)
    void testGetAll() {
        List<Coupon> coupons = couponService.getAll();
        assertNotNull(coupons);
        assertFalse(coupons.isEmpty());
        System.out.println("Total coupons: " + coupons.size());
    }

    @Test
    @Order(10)
    void testDelete() {
        couponService.delete(testCoupon.getCouponId());
        Coupon deleted = couponService.read(testCoupon.getCouponId());
        assertNull(deleted);
        System.out.println("Coupon deleted successfully");
    }
}