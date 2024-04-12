package com.example.ordering_lecture.item.controller;

import com.example.ordering_lecture.common.OrTopiaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service")
public interface MemberServiceClient {
    @GetMapping("/member/search/{email}")
    Long searchIdByEmail(@PathVariable("email") String email);
}
