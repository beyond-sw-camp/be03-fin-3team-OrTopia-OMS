package com.example.ordering_lecture.member.controller;

import com.example.ordering_lecture.member.dto.Buyer.MemberLikeSellerRequestDto;
import com.example.ordering_lecture.member.dto.Buyer.MemberResponseDto;
import com.example.ordering_lecture.member.dto.Buyer.MemberRequestDto;
import com.example.ordering_lecture.member.dto.Buyer.MemberUpdateDto;
import com.example.ordering_lecture.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {
    @Autowired
    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
    /*
    BUYER 관련 API
     */
    // 사용자 생성
    @PostMapping("/member/create")
    public Object createMember(@RequestBody MemberRequestDto memberRequestDto){
        MemberResponseDto memberResponseDto = memberService.createMember(memberRequestDto);
        return memberResponseDto;
    }
    // 사용자 상세조회
    @GetMapping("/member/{id}")
    public Object findMember(@PathVariable Long id){
        return memberService.findById(id);
    }
    // 판매자 전체조회
    @GetMapping("/member/All")
    public Object findAllMembers(){
        List<MemberResponseDto> memberResponseDtos = memberService.findAllMembers();
        return memberResponseDtos;
    }
    // 사용자 전체조회
    @GetMapping("/member/members")
    public Object findMembers(){
        List<MemberResponseDto> memberResponseDtos = memberService.findMembers();
        return memberResponseDtos;
    }
    // 사용자 정보수정
    @PatchMapping("/member/{id}")
    public Object updateMember(@PathVariable Long id, @RequestBody MemberUpdateDto memberUpdateDto){
        return memberService.updateMember(id, memberUpdateDto);
    }
    // 사용자 삭제(delYN=true)
    @DeleteMapping("/member/{id}")
    public String deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return "ok";
    }
    // 판매자 즐겨찾기
    @PostMapping("/member/likeSeller")
    public Object likeSeller(@RequestBody MemberLikeSellerRequestDto memberLikeSellerRequestDto){
        memberService.likeSeller(memberLikeSellerRequestDto);
        return "ok";
    }
    // 즐겨찾기 한 판매자 목록 조회
    @GetMapping("/member/{id}/likeSellers")
    public Object likeSellers(@PathVariable Long id){
        return memberService.likeSellers(id);
    }
}
