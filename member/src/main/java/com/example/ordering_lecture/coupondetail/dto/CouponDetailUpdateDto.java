package com.example.ordering_lecture.coupondetail.dto;

import com.example.ordering_lecture.address.domain.Address;
import com.example.ordering_lecture.coupondetail.domain.CouponDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDetailUpdateDto {
    private String name;
    private String startDate;
    private String endDate;
    private int rateDiscount;
    private int fixDiscount;
    private Long sellerId;

    public CouponDetail toUpdate(CouponDetail couponDetail) {
        if (name != null) {
            couponDetail.updateName(name);
        }
        if (startDate != null) {
            couponDetail.updateStartDate(startDate);
        }
        if (endDate != null) {
            couponDetail.updateEndDate(endDate);
        }
        if (rateDiscount != 0) { // Assuming 0 is an invalid value for discount
            couponDetail.updateRateDiscount(rateDiscount);
        }
        if (fixDiscount != 0) { // Assuming 0 is an invalid value for discount
            couponDetail.updateFixDiscount(fixDiscount);
        }
        if (sellerId != null) {
            couponDetail.updateSellerId(sellerId);
        }
        return couponDetail;
    }
}
