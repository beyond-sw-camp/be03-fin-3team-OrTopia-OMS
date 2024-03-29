package com.example.ordering_lecture.member.dto.Buyer;

import com.example.ordering_lecture.member.domain.Gender;
import com.example.ordering_lecture.member.domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberResponseDto {
    private Long id;
    private String email;
    private String name;
    private byte age;
    private Gender gender;
    private String phoneNumber;

    public static MemberResponseDto toDto(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .age(member.getAge())
                .gender(member.getGender())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
