package com.example.ordering_lecture.coupon.repository;

import com.example.ordering_lecture.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
}
