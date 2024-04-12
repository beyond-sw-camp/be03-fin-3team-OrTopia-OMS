package com.example.ordering_lecture.membercoupon.cotroller;

import com.example.ordering_lecture.coupon.dto.CouponRequestDto;
import com.example.ordering_lecture.coupondetail.domain.CouponDetail;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailResponseDto;
import com.example.ordering_lecture.membercoupon.dto.MemberCouponRequestDto;
import com.example.ordering_lecture.membercoupon.service.MemberCouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("member_server")
public class MemberCouponCotroller {
    public final MemberCouponService memberCouponService;

    public MemberCouponCotroller(MemberCouponService memberCouponService) {
        this.memberCouponService = memberCouponService;
    }

    @PostMapping("/membercoupon/create")
    public Object createMemberCoupon(@RequestBody MemberCouponRequestDto memberCouponRequestDto){
        return memberCouponService.createMemberCoupon(memberCouponRequestDto);
    }
    @GetMapping("/member-coupons/{memberId}/coupon-details")
    public ResponseEntity<List<CouponDetailResponseDto>> getCouponDetailsByMemberId(@PathVariable Long memberId) {
        List<CouponDetailResponseDto> couponDetails = memberCouponService.findCouponDetailsByMemberId(memberId);
        return ResponseEntity.ok(couponDetails);
    }
    @DeleteMapping("/usecoupon/{memberId}/{couponDetailId}")
    public String useCoupon(@PathVariable Long memberId, @PathVariable Long couponDetailId){
        memberCouponService.useCoupon(memberId, couponDetailId);
        return "ok";
    }
}
