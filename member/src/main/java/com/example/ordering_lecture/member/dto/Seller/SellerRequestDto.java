package com.example.ordering_lecture.member.dto.Seller;

import com.example.ordering_lecture.member.domain.BusinessType;
import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.domain.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerRequestDto {
    @NotEmpty(message = "BUSINESSNUMBER_IS_ESSENTIAL")
    private String businessNumber;
    @NotEmpty(message = "COMPANYNAME_IS_ESSENTIAL")
    private String companyName;
    @NotEmpty(message = "BUSINESSTYPE_IS_ESSENTIAL")
    private BusinessType businessType;

    public Seller toEntity(Member member){
        Seller seller = Seller.builder()
                .businessNumber(this.businessNumber)
                .companyName(this.companyName)
                .businessType(this.businessType)
                .member(member)
               .build();
        return seller;
    }
}
