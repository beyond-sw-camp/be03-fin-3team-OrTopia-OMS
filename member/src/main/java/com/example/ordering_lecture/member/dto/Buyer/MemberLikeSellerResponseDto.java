package com.example.ordering_lecture.member.dto.Buyer;

import com.example.ordering_lecture.member.domain.LikedSeller;
import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.domain.Seller;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MemberLikeSellerResponseDto {
    private Long id;
    private Seller seller;
    private Member buyer;
    private LocalDateTime createdTime;

    public static MemberLikeSellerResponseDto toDto(LikedSeller likedSeller) {
        return MemberLikeSellerResponseDto.builder()
                .id(likedSeller.getId())
                .seller(likedSeller.getSeller())
                .buyer(likedSeller.getBuyer())
                .createdTime(likedSeller.getCreatedTime())
                .build();
    }
}
