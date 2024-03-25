package com.example.ordering_lecture.member.controller;

import com.example.ordering_lecture.common.CommonResponse;
import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.dto.MemberCreateReqDto;
import com.example.ordering_lecture.member.dto.MemberLoginReqDto;
import com.example.ordering_lecture.member.dto.MemberResponseDto;
import com.example.ordering_lecture.member.service.MemberService;
import com.example.ordering_lecture.securities.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class    MemberController {
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final MemberService memberService;

    public MemberController(JwtTokenProvider jwtTokenProvider, MemberService memberService){
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    @PostMapping("member/create")
    public ResponseEntity<CommonResponse> memberCreate(@Valid @RequestBody MemberCreateReqDto memberCreateReqDt){
        Member member = memberService.create(memberCreateReqDt);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED,"member created success",member.getId()),HttpStatus.CREATED);
    }

    @PostMapping("/doLogin")
    public ResponseEntity<CommonResponse> memberLogin(@Valid @RequestBody MemberLoginReqDto memberLoginReqDto){
        Member member = memberService.doLogin(memberLoginReqDto);
        // 토큰  생성 로직
        String jwtToken = jwtTokenProvider.createToken(member.getEmail(),member.getRole().toString());
        Map<String,Object> member_info = new HashMap<>();
        member_info.put("member_id",member.getId());
        member_info.put("token",jwtToken);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK,"member success logined",member_info),HttpStatus.OK);
    }

    @GetMapping("member/{id}")
    public MemberResponseDto memberFind(@PathVariable Long id){
        return memberService.findById(id);
    }

    @GetMapping("/member/myInfo")
    public MemberResponseDto myInfo(@RequestHeader("myEmail") String myEmail){
        return memberService.findMyInfo(myEmail);
    }

    @GetMapping("member/findByEmail")
    public MemberResponseDto memberFind(@RequestParam String email){
        return memberService.findByEmail(email);
    }

    @GetMapping("/members")
    public List<MemberResponseDto> members(){
        return memberService.findAll();
    }

//    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.username")
//    @GetMapping("/member/myoders")
//    public List<OrderResDto> myOrders(){
//        return orderService.findMyOrder();
//    }

//    관리자가 회원목록을 톻해서 특정 회원의 주문을 취소
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/member/{id}/orders")
//    public List<OrderResDto> memberOrders(@PathVariable Long id){
//        return orderService.findMyOrder(id);
//    }
}
