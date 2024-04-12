package com.example.ordering_lecture.address.dto;

import com.example.ordering_lecture.address.domain.Address;
import com.example.ordering_lecture.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDto {
    @NotNull
    private String name;
    @NotNull
    private String sido;
    @NotNull
    private String sigungu;
    @NotNull
    private String bname;
    @NotNull
    private String roadAddress;
    @NotNull
    private String zonecode;
    @NotNull
    private String detail;

    public Address toEntity(Member member){
        return Address.builder()
                .name(this.name)
                .sido(this.sido)
                .sigungu(this.sigungu)
                .bname(this.bname)
                .roadAddress(this.roadAddress)
                .zonecode(this.zonecode)
                .detail(this.detail)
                .member(member)
                .build();
    }
}