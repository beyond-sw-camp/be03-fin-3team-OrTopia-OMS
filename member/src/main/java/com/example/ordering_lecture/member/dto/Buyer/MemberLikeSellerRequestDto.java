package com.example.ordering_lecture.member.dto.Buyer;

import com.example.ordering_lecture.member.domain.LikedSeller;
import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.domain.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLikeSellerRequestDto {
    private Long sellerID;
    private Long buyerID;
    public LikedSeller toEntity(Member member, Seller seller){
        LikedSeller likedSeller = LikedSeller.builder()
                .buyer(member)
                .seller(seller)
                .build();
        return likedSeller;
    }
}
