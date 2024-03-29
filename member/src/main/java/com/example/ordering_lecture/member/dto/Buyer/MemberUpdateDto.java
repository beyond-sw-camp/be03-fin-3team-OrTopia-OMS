package com.example.ordering_lecture.member.dto.Buyer;

import com.example.ordering_lecture.member.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateDto {
    private String email;
    private String name;
    private String password;
    private byte age;
    private Gender gender;
    private String phoneNumber;
}
