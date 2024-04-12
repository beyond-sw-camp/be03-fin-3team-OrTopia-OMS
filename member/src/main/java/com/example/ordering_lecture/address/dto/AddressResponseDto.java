package com.example.ordering_lecture.address.dto;

import com.example.ordering_lecture.address.domain.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponseDto {
    private Long id;
    private String name;
    private String sido;
    private String sigungu;
    private String bname;
    private String roadAddress;
    private String zonecode;
    private String detail;
    private Long memberId;

    public static AddressResponseDto toDto(Address address){
        return AddressResponseDto.builder()
                .id(address.getId())
                .name(address.getName())
                .sido(address.getSido())
                .sigungu(address.getSigungu())
                .bname(address.getBname())
                .roadAddress(address.getRoadAddress())
                .zonecode(address.getZonecode())
                .detail(address.getDetail())
                .memberId(address.getMember().getId()) // 수정: 올바른 memberId 설정
                .build();
    }
}