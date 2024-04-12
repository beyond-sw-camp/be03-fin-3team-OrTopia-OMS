package com.example.ordering_lecture.coupondetail.controller;


import com.example.ordering_lecture.address.dto.AddressResponseDto;
import com.example.ordering_lecture.address.dto.AddressUpdateDto;
import com.example.ordering_lecture.coupondetail.domain.CouponDetail;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailRequestDto;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailResponseDto;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailUpdateDto;
import com.example.ordering_lecture.coupondetail.service.CouponDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member_server")
public class CouponDetailController {
    private final CouponDetailService couponDetailService;

    public CouponDetailController(CouponDetailService couponDetailService) {
        this.couponDetailService = couponDetailService;
    }

    @PostMapping("/coupondetail/create")
    public String createCoupon(@RequestBody CouponDetailRequestDto couponDetailRequestDto) {
        couponDetailService.createCouponDetail(couponDetailRequestDto);
        return "ok";
    }

    @GetMapping("/coupondetail/{sellerId}")
    public ResponseEntity<List<CouponDetail>> getAllCouponsBySellerId(@PathVariable Long sellerId) {
        List<CouponDetail> couponDetails = couponDetailService.findAllBySellerId(sellerId);
        return ResponseEntity.ok(couponDetails);
    }

    @PatchMapping("couponupdate/{id}")
    public CouponDetailResponseDto updateCoupon(@PathVariable Long id, @RequestBody CouponDetailUpdateDto couponDetailUpdateDto) {
        return couponDetailService.updateCoupons(id, couponDetailUpdateDto);
    }
    @DeleteMapping("coupon/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {
        couponDetailService.deleteCoupon(id);
        return "ok";
    }
}
