package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Coupon;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CouponFactoryTest {

    @Test
    void testBuildCoupon_Success() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Coupon coupon = CouponFactory.buildCoupon(
                "CPN001",
                "SAVE20",
                20.0,
                futureDate,
                "ACTIVE"
        );

        assertNotNull(coupon);
        assertEquals("CPN001", coupon.getCouponId());
        assertEquals("SAVE20", coupon.getCode());
        assertEquals(20.0, coupon.getDiscountPercentage());
        assertEquals(futureDate, coupon.getExpiryDate());
        assertEquals("ACTIVE", coupon.getStatus());
    }

    @Test
    void testBuildCoupon_ExpiredStatus_Success() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);

        Coupon coupon = CouponFactory.buildCoupon(
                "CPN002",
                "OLDCODE",
                15.0,
                futureDate,
                "EXPIRED"
        );

        assertNotNull(coupon);
        assertEquals("EXPIRED", coupon.getStatus());
    }

    @Test
    void testBuildCoupon_MaxDiscount_Success() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(7);

        Coupon coupon = CouponFactory.buildCoupon(
                "CPN003",
                "MAX100",
                100.0,
                futureDate,
                "ACTIVE"
        );

        assertNotNull(coupon);
        assertEquals(100.0, coupon.getDiscountPercentage());
    }

    @Test
    void testBuildCoupon_MinDiscount_Success() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(7);

        Coupon coupon = CouponFactory.buildCoupon(
                "CPN004",
                "MIN1",
                0.01,
                futureDate,
                "ACTIVE"
        );

        assertNotNull(coupon);
        assertEquals(0.01, coupon.getDiscountPercentage());
    }

    @Test
    void testBuildCoupon_NullCouponId_ThrowsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    null,
                    "SAVE20",
                    20.0,
                    futureDate,
                    "ACTIVE"
            );
        });
        assertEquals("Coupon ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCoupon_EmptyCouponId_ThrowsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "",
                    "SAVE20",
                    20.0,
                    futureDate,
                    "ACTIVE"
            );
        });
        assertEquals("Coupon ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCoupon_NullCode_ThrowsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    null,
                    20.0,
                    futureDate,
                    "ACTIVE"
            );
        });
        assertEquals("Coupon code cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCoupon_EmptyCode_ThrowsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    "   ",
                    20.0,
                    futureDate,
                    "ACTIVE"
            );
        });
        assertEquals("Coupon code cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCoupon_ZeroDiscount_ThrowsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    "SAVE0",
                    0.0,
                    futureDate,
                    "ACTIVE"
            );
        });
        assertEquals("Discount percentage must be between 0 and 100", exception.getMessage());
    }

    @Test
    void testBuildCoupon_NegativeDiscount_ThrowsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    "NEGATIVE",
                    -10.0,
                    futureDate,
                    "ACTIVE"
            );
        });
        assertEquals("Discount percentage must be between 0 and 100", exception.getMessage());
    }

    @Test
    void testBuildCoupon_DiscountOver100_ThrowsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    "OVER100",
                    101.0,
                    futureDate,
                    "ACTIVE"
            );
        });
        assertEquals("Discount percentage must be between 0 and 100", exception.getMessage());
    }

    @Test
    void testBuildCoupon_NullExpiryDate_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    "SAVE20",
                    20.0,
                    null,
                    "ACTIVE"
            );
        });
        assertEquals("Expiry date cannot be null", exception.getMessage());
    }

    @Test
    void testBuildCoupon_PastExpiryDate_ThrowsException() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    "EXPIRED",
                    20.0,
                    pastDate,
                    "ACTIVE"
            );
        });
        assertEquals("Expiry date must be in the future", exception.getMessage());
    }

    @Test
    void testBuildCoupon_ExpiryDateInPast_ThrowsException() {
        LocalDateTime pastDate = LocalDateTime.now().minusYears(1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    "OLDCOUPON",
                    50.0,
                    pastDate,
                    "EXPIRED"
            );
        });
        assertEquals("Expiry date must be in the future", exception.getMessage());
    }

    @Test
    void testBuildCoupon_NullStatus_ThrowsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    "SAVE20",
                    20.0,
                    futureDate,
                    null
            );
        });
        assertEquals("Status cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCoupon_EmptyStatus_ThrowsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    "SAVE20",
                    20.0,
                    futureDate,
                    ""
            );
        });
        assertEquals("Status cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCoupon_InvalidStatus_ThrowsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    "SAVE20",
                    20.0,
                    futureDate,
                    "INVALID"
            );
        });
        assertEquals("Status must be either ACTIVE or EXPIRED", exception.getMessage());
    }

    @Test
    void testBuildCoupon_LowercaseStatus_ThrowsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    "SAVE20",
                    20.0,
                    futureDate,
                    "active"
            );
        });
        assertEquals("Status must be either ACTIVE or EXPIRED", exception.getMessage());
    }

    @Test
    void testBuildCoupon_MixedCaseStatus_ThrowsException() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CouponFactory.buildCoupon(
                    "CPN001",
                    "SAVE20",
                    20.0,
                    futureDate,
                    "Active"
            );
        });
        assertEquals("Status must be either ACTIVE or EXPIRED", exception.getMessage());
    }
}