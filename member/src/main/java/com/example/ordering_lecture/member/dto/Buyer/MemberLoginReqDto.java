package com.example.ordering_lecture.member.dto.Buyer;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class MemberLoginReqDto {
    @NotEmpty(message = "email is essential")
    @Email(message = "email is not valid")
    private String email;
    @Size(min=4, message = "minimum length is 4")
    private String password;
}
