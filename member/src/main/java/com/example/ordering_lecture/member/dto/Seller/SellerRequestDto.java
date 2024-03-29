package com.example.ordering_lecture.member.dto.Seller;

import com.example.ordering_lecture.member.domain.BusinnessType;
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
    @NotEmpty(message = "businnessNumber is essential")
    private String businnessNumber;
    @NotEmpty(message = "companyName is essential")
    private String companyName;
    @NotEmpty(message = "businnessType is essential")
    private BusinnessType businnessType;

    public Seller toEntity(Member member){
        Seller seller = Seller.builder()
                .businnessNumber(this.businnessNumber)
                .companyName(this.companyName)
                .businnessType(this.businnessType)
                .member(member)
               .build();
        return seller;
    }
}
