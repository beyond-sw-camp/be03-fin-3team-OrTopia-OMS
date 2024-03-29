package com.example.ordering_lecture.member.dto.Seller;

import com.example.ordering_lecture.member.domain.BannedSeller;
import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.dto.Buyer.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class BannedSellerResponseDto {
    private Long id;
    private String details;
    private String startTime;
    private String endTime;

    public static BannedSellerResponseDto toDto(BannedSeller bannedSeller) {
        return BannedSellerResponseDto.builder()
                .id(bannedSeller.getId())
                .details(bannedSeller.getDetails())
                .startTime(bannedSeller.getStartTime())
                .endTime(bannedSeller.getEndTime())
                .build();
    }
}
