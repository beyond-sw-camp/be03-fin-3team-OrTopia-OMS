package com.example.ordering_lecture.coupondetail.service;

import com.example.ordering_lecture.address.domain.Address;
import com.example.ordering_lecture.address.dto.AddressResponseDto;
import com.example.ordering_lecture.address.dto.AddressUpdateDto;
import com.example.ordering_lecture.coupondetail.domain.CouponDetail;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailRequestDto;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailResponseDto;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailUpdateDto;
import com.example.ordering_lecture.coupondetail.repository.CouponDetailRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponDetailService {
    private final CouponDetailRepository couponDetailRepository;

    public CouponDetailService(CouponDetailRepository couponDetailRepository) {
        this.couponDetailRepository = couponDetailRepository;
    }
    public CouponDetail createCouponDetail(CouponDetailRequestDto couponDetailRequestDto){
        CouponDetail couponDetail = couponDetailRequestDto.toEntity();
        return couponDetailRepository.save(couponDetail);
    }
    public List<CouponDetail> findAllBySellerId(Long sellerId) {
        return couponDetailRepository.findBySellerId(sellerId);
    }
    @Transactional
    public CouponDetailResponseDto updateCoupons(Long id, CouponDetailUpdateDto couponDetailUpdateDto) {
        CouponDetail couponDetail = couponDetailRepository.findById(id).orElseThrow();
        couponDetail = couponDetailUpdateDto.toUpdate(couponDetail);
        return CouponDetailResponseDto.toDto(couponDetail);
    }
    public void deleteCoupon(Long id) {
        couponDetailRepository.deleteById(id);
    }
}
