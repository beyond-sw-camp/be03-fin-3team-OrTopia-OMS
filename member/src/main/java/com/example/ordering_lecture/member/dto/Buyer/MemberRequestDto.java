package com.example.ordering_lecture.member.dto.Buyer;

import com.example.ordering_lecture.member.domain.Gender;
import com.example.ordering_lecture.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {
    @NotEmpty(message = "EMAIL_IS_ESSENTIAL")
    @Email(message = "EMAIL_IS_NOT_VALID")
    private String email;
    @NotEmpty(message = "NAME_IS_ESSENTIAL")
    private String name;
    @NotEmpty(message = " PASSWORD_IS_ESSENTIAL")
    @Size(min=4, message = "PASSWORD_LENGTH")
    private String password;
    @NotEmpty(message = "AGE_IS_ESSENTIAL")
    private byte age;
    @NotEmpty(message = "GENDER_IS_ESSENTIAL")
    private Gender gender;
    @NotEmpty(message = "PHONENUMBER_IS_ESSENTIAL")
    private String phoneNumber;

    public Member toEntity(){
        Member member = Member.builder()
               .email(this.getEmail())
               .name(this.getName())
               .password(this.getPassword())
               .age(this.getAge())
               .gender(this.getGender())
               .phoneNumber(this.getPhoneNumber())
               .build();
        return member;
    }
}
