package com.example.ordering_lecture.member.dto.Seller;

import com.example.ordering_lecture.member.domain.BusinnessType;
import com.example.ordering_lecture.member.domain.Seller;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SellerResponseDto {
    private Long id;
    private String businnessNumber;
    private String companyName;
    private BusinnessType businnessType;
    private Long totalScore;
    private Long memberID;

    public static SellerResponseDto toDto(Seller seller) {
        return SellerResponseDto.builder()
                .id(seller.getId())
                .businnessNumber(seller.getBusinnessNumber())
                .companyName(seller.getCompanyName())
                .businnessType(seller.getBusinnessType())
                .totalScore(seller.getTotalScore())
                .memberID(seller.getMember().getId())
                .build();
    }
}
