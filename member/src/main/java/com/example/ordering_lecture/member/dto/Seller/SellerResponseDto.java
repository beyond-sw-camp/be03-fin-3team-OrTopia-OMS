package com.example.ordering_lecture.member.dto.Seller;

import com.example.ordering_lecture.member.domain.BusinessType;
import com.example.ordering_lecture.member.domain.Seller;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SellerResponseDto {
    private Long id;
    private String businessNumber;
    private String companyName;
    private BusinessType businessType;
    private Long totalScore;
    private Long memberID;

    public static SellerResponseDto toDto(Seller seller) {
        return SellerResponseDto.builder()
                .id(seller.getId())
                .businessNumber(seller.getBusinessNumber())
                .companyName(seller.getCompanyName())
                .businessType(seller.getBusinessType())
                .totalScore(seller.getTotalScore())
                .memberID(seller.getMember().getId())
                .build();
    }
}
