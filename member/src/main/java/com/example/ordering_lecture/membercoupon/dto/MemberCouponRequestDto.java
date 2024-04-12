package com.example.ordering_lecture.membercoupon.dto;

import com.example.ordering_lecture.coupondetail.domain.CouponDetail;
import com.example.ordering_lecture.membercoupon.domain.MemberCoupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCouponRequestDto {
    private List<Long> memberIdList;
    private Long couponDetailId;
    }
