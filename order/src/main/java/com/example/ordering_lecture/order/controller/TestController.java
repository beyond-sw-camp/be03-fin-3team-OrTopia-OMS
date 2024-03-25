package com.example.ordering_lecture.order.controller;

import com.example.ordering_lecture.order.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {
    // 유레카한테 물어보감, (유레카의 주소는 yml에서 알고 있으니까)
    private final String MEMBER_API = "http://member-service/";

    @Autowired
    private final RestTemplate restTemplate;
    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("test/resttemplate")
    public void restTemplateTest(){
        String url = MEMBER_API+"member/1";
        MemberDto member = restTemplate.getForObject(url, MemberDto.class);
        System.out.println(member);
    }
}
