package com.example.ordering_lecture.coupondetail.dto;

import com.example.ordering_lecture.coupon.domain.Coupon;
import com.example.ordering_lecture.coupondetail.domain.CouponDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDetailRequestDto {
    @NotNull
    private String name;
    private String startDate;
    private String endDate;
    private int rateDiscount;
    private int fixDiscount;
    private Long sellerId;

    public CouponDetail toEntity(){
        CouponDetail couponDetail  = CouponDetail.builder()
                .name(this.getName())
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .rateDiscount(this.getRateDiscount())
                .fixDiscount(this.getFixDiscount())
                .sellerId(this.getSellerId())
                .build();
        return couponDetail;
    }
}