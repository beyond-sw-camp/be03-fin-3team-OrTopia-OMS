package com.example.ordering_lecture.coupon.dto;

import com.example.ordering_lecture.coupon.domain.Coupon;
import com.example.ordering_lecture.coupondetail.domain.CouponDetail;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequestDto {
    private List<Long> itemIdList;
    private Long couponDetailId;
}