package com.example.ordering_lecture.address.dto;

import com.example.ordering_lecture.address.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateDto {
    private String name;
    private String sido;
    private String sigungu;
    private String bname;
    private String roadAddress;
    private String zonecode;
    private String detail;
}
