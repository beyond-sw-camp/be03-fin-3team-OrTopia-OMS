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
    @NotEmpty(message = "email is essential")
    @Email(message = "email is not valid")
    private String email;
    @NotEmpty(message = "name is essential")
    private String name;
    @NotEmpty(message = "password is essential")
    @Size(min=4, message = "minimum length is 4")
    private String password;
    @NotEmpty(message = "age is essential")
    private byte age;
    @NotEmpty(message = "gender is essential")
    private Gender gender;
    @NotEmpty(message = "phoneNumber is essential")
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
