package com.example.ordering_lecture.coupon.controller;

import com.example.ordering_lecture.coupon.dto.CouponRequestDto;
import com.example.ordering_lecture.coupon.service.CouponService;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member_server")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }
    @PostMapping("/coupon/create")
    public Object createCoupon(@RequestBody CouponRequestDto couponRequestDto){
        return couponService.createCoupon(couponRequestDto);
    }
}