package com.example.ordering_lecture.order.dto;

import lombok.Data;

// 데이터를 받아올때, 데이터 바인딩을 쉽게 하기위해서 Dto를 받아옴.
@Data
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private String city;
    private String street;
    private String zipcode;
    private int orderCount;
}
