package com.example.ordering_lecture.member.dto.Buyer;

import com.example.ordering_lecture.member.domain.LikedSeller;
import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.domain.Seller;
import com.example.ordering_lecture.member.dto.Seller.SellerResponseDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MemberLikeSellerResponseDto {
    private Long id;
    private SellerResponseDto seller;
    private MemberResponseDto buyer;
    private LocalDateTime createdTime;

    public static MemberLikeSellerResponseDto toDto(LikedSeller likedSeller) {
        return MemberLikeSellerResponseDto.builder()
                .id(likedSeller.getId())
                .seller(SellerResponseDto.toDto(likedSeller.getSeller()))
                .buyer(MemberResponseDto.toDto(likedSeller.getBuyer()))
                .createdTime(likedSeller.getCreatedTime())
                .build();
    }
}
