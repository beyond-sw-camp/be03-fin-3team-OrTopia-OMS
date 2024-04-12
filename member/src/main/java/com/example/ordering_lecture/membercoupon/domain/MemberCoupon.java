package com.example.ordering_lecture.membercoupon.domain;

import com.example.ordering_lecture.coupondetail.domain.CouponDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long memberId;
    @JoinColumn(name="couponDetail_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CouponDetail couponDetail;
}