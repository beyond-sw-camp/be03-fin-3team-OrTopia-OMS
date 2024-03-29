package com.example.ordering_lecture.member.dto.Seller;

import com.example.ordering_lecture.member.domain.BannedSeller;
import com.example.ordering_lecture.member.domain.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannedSellerRequestDto {
    private String details;
    private String startTime;
    private String endTime;
    public BannedSeller toEntity(Seller seller){
        BannedSeller bannedSeller = BannedSeller.builder()
                .seller(seller)
                .details(this.getDetails())
                .startTime(this.getStartTime())
                .endTime(this.getEndTime())
                .build();
        return bannedSeller;
    }
}
