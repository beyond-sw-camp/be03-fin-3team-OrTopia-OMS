package com.example.ordering_lecture.membercoupon.service;

import com.example.ordering_lecture.coupondetail.domain.CouponDetail;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailResponseDto;
import com.example.ordering_lecture.coupondetail.repository.CouponDetailRepository;
import com.example.ordering_lecture.membercoupon.domain.MemberCoupon;
import com.example.ordering_lecture.membercoupon.dto.MemberCouponRequestDto;
import com.example.ordering_lecture.membercoupon.dto.MemberCouponResponseDto;
import com.example.ordering_lecture.membercoupon.repository.MemberCouponRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberCouponService {
    private final MemberCouponRepository memberCouponRepository;
    private final CouponDetailRepository couponDetailRepository;

    public MemberCouponService(MemberCouponRepository memberCouponRepository, CouponDetailRepository couponDetailRepository) {
        this.memberCouponRepository = memberCouponRepository;
        this.couponDetailRepository = couponDetailRepository;
    }
    public List<MemberCouponResponseDto> createMemberCoupon(MemberCouponRequestDto memberCouponRequestDto) {
        //TODO:에러메세지 수정
        CouponDetail couponDetail = couponDetailRepository.findById(memberCouponRequestDto.getCouponDetailId()).orElseThrow();
        List<MemberCoupon> memberCoupons = new ArrayList<>();
        for (Long memberId : memberCouponRequestDto.getMemberIdList()) {
            MemberCoupon memberCoupon = MemberCoupon.builder()
                    .memberId(memberId)
                    .couponDetail(couponDetail)
                    .build();
            memberCoupons.add(memberCoupon);
            memberCouponRepository.save(memberCoupon);
        }
        return memberCoupons.stream()
                .map(MemberCouponResponseDto::toDto)
                .collect(Collectors.toList());
    }
    public List<CouponDetailResponseDto> findCouponDetailsByMemberId(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        return memberCoupons.stream()
                .map(memberCoupon -> new CouponDetailResponseDto(
                        memberCoupon.getCouponDetail().getId(),
                        memberCoupon.getCouponDetail().getName(),
                        memberCoupon.getCouponDetail().getStartDate(),
                        memberCoupon.getCouponDetail().getEndDate(),
                        memberCoupon.getCouponDetail().getRateDiscount(),
                        memberCoupon.getCouponDetail().getFixDiscount()
                ))
                .collect(Collectors.toList());
    }
    public List<MemberCouponResponseDto> myCoupons(Long id) {
        return memberCouponRepository.findByMemberId(id)
                .stream()
                .map(a->MemberCouponResponseDto.toDto(a))
                .collect(Collectors.toList());
    }
    @Transactional
    public void useCoupon(Long memberId, Long couponDetailId) {
        memberCouponRepository.deleteByMemberIdAndCouponDetailId(memberId, couponDetailId);
    }
}