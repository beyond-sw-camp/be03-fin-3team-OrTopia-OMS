package com.example.ordering_lecture.coupon.service;

import com.example.ordering_lecture.coupon.domain.Coupon;
import com.example.ordering_lecture.coupon.dto.CouponRequestDto;
import com.example.ordering_lecture.coupon.dto.CouponResponseDto;
import com.example.ordering_lecture.coupon.repository.CouponRepository;
import com.example.ordering_lecture.coupondetail.domain.CouponDetail;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailRequestDto;
import com.example.ordering_lecture.coupondetail.repository.CouponDetailRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponDetailRepository couponDetailRepository;

    public CouponService(CouponRepository couponRepository, CouponDetailRepository couponDetailRepository) {
        this.couponRepository = couponRepository;
        this.couponDetailRepository = couponDetailRepository;
    }

    public List<CouponResponseDto> createCoupon(CouponRequestDto couponRequestDto) {
        //TODO:에러메세지 수정
        CouponDetail couponDetail = couponDetailRepository.findById(couponRequestDto.getCouponDetailId()).orElseThrow();
        List<Coupon> coupons = new ArrayList<>();
        for (Long itemId : couponRequestDto.getItemIdList()) {
            Coupon coupon = Coupon.builder()
                    .itemId(itemId)
                    .couponDetail(couponDetail)
                    .build();
            coupons.add(coupon);
            couponRepository.save(coupon);
        }
        return coupons.stream()
                .map(a->CouponResponseDto.toDto(a,couponDetail))
                .collect(Collectors.toList());
    }
}