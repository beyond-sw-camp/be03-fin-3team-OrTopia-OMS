package com.example.ordering_lecture.coupon.dto;

import com.example.ordering_lecture.coupon.domain.Coupon;
import com.example.ordering_lecture.coupondetail.domain.CouponDetail;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailResponseDto;
import com.example.ordering_lecture.coupondetail.dto.CouponDetailUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponseDto {
    private Long id;
    private Long itemId;
    private CouponDetailResponseDto couponDetailResponseDto;

    public static CouponResponseDto toDto(Coupon coupon,CouponDetail couponDetail){
        return CouponResponseDto.builder()
                .id(coupon.getId())
                .itemId(coupon.getItemId())
                .couponDetailResponseDto(CouponDetailResponseDto.toDto(couponDetail))
                .build();
    }
}
