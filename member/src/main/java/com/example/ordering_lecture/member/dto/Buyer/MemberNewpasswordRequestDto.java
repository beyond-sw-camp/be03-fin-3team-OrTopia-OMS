package com.example.ordering_lecture.member.dto.Buyer;

import com.example.ordering_lecture.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberNewpasswordRequestDto {
    @Size(min=4, message = "PASSWORD_LENGTH")
    private String password;

}
