package com.example.ordering_lecture.coupondetail.dto;

import com.example.ordering_lecture.address.domain.Address;
import com.example.ordering_lecture.address.dto.AddressResponseDto;
import com.example.ordering_lecture.coupondetail.domain.CouponDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDetailResponseDto {
    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private int rateDiscount;
    private int fixDiscount;
    public static CouponDetailResponseDto toDto(CouponDetail couponDetail){
        return CouponDetailResponseDto.builder()
                .id(couponDetail.getId())
                .name(couponDetail.getName())
                .startDate(couponDetail.getStartDate())
                .endDate(couponDetail.getEndDate())
                .rateDiscount(couponDetail.getRateDiscount())
                .fixDiscount(couponDetail.getFixDiscount())
                .build();
    }
}
