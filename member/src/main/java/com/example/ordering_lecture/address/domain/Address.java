package com.example.ordering_lecture.address.domain;

import com.example.ordering_lecture.address.dto.AddressUpdateDto;
import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.dto.Buyer.MemberUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 주소의 이름

    @Column(nullable = false)
    private String sido; // 시도

    @Column(nullable = false)
    private String sigungu; // 시군구

    @Column(nullable = false)
    private String bname; // 도로명

    @Column(nullable = false)
    private String roadAddress; // 도로명 주소

    @Column(nullable = false)
    private String detail; // 상세 주소

    @Column(nullable = false)
    private String zonecode; // 우편번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void updateAddress(AddressUpdateDto addressUpdateDto) {
        if (addressUpdateDto.getName() != null && !addressUpdateDto.getName().equals(this.name)) {
            this.name = addressUpdateDto.getName();
        }
        if (addressUpdateDto.getSido() != null && !addressUpdateDto.getSido().equals(this.sido)) {
            this.sido = addressUpdateDto.getSido();
        }
        if (addressUpdateDto.getSigungu() != null && !addressUpdateDto.getSigungu().equals(this.sigungu)) {
            this.sigungu = addressUpdateDto.getSigungu();
        }
        if (addressUpdateDto.getBname() != null && !addressUpdateDto.getBname().equals(this.bname)) {
            this.bname = addressUpdateDto.getBname();
        }
        if (addressUpdateDto.getRoadAddress() != null && !addressUpdateDto.getRoadAddress().equals(this.roadAddress)) {
            this.roadAddress = addressUpdateDto.getRoadAddress();
        }
        if (addressUpdateDto.getDetail() != null && !addressUpdateDto.getRoadAddress().equals(this.detail))
            this.detail = addressUpdateDto.getDetail();

        if (addressUpdateDto.getZonecode() != null && !addressUpdateDto.getZonecode().equals(this.zonecode)) {
            this.zonecode = addressUpdateDto.getZonecode();
        }
    }
}