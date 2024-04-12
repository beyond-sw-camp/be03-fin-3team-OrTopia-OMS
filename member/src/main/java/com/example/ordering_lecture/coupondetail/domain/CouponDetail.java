package com.example.ordering_lecture.coupondetail.domain;

import com.example.ordering_lecture.coupon.domain.Coupon;
import com.example.ordering_lecture.membercoupon.domain.MemberCoupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String startDate;
    @Column(nullable = false)
    private String endDate;
    @Column
    private int rateDiscount;
    @Column
    private int fixDiscount;
    @Column(nullable = false)
    private Long sellerId;

    @OneToMany(mappedBy = "couponDetail",fetch = FetchType.LAZY, orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Coupon> coupons;

    @OneToMany(mappedBy = "couponDetail",fetch = FetchType.LAZY, orphanRemoval = true,cascade = CascadeType.ALL)
    private List<MemberCoupon> memberCoupons;
    public void updateName(String name) {
        this.name = name;
    }

    public void updateStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void updateEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void updateRateDiscount(int rateDiscount) {
        this.rateDiscount = rateDiscount;
    }

    public void updateFixDiscount(int fixDiscount) {
        this.fixDiscount = fixDiscount;
    }

    public void updateSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}