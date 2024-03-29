package com.example.ordering_lecture.member.dto.Seller;

import com.example.ordering_lecture.member.domain.BusinnessType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerUpdateDto {
    private String businnessNumber;
    private String companyName;
    private BusinnessType businnessType;
}
