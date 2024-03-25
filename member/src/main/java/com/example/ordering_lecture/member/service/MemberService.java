package com.example.ordering_lecture.member.service;

import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.dto.MemberCreateReqDto;
import com.example.ordering_lecture.member.dto.MemberLoginReqDto;
import com.example.ordering_lecture.member.dto.MemberResponseDto;
import com.example.ordering_lecture.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository,PasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member create(MemberCreateReqDto memberCreateReqDto){
        if(memberRepository.findByEmail(memberCreateReqDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
        memberCreateReqDto.setPassword(passwordEncoder.encode(memberCreateReqDto.getPassword()));
        return memberRepository.save(Member.toMember(memberCreateReqDto));
    }

    public Member doLogin(MemberLoginReqDto memberLoginReqDto) throws IllegalArgumentException{
        Member member = memberRepository.findByEmail(memberLoginReqDto.getEmail())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 이메일 입니다."));
        if(!passwordEncoder.matches(memberLoginReqDto.getPassword(),member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }
        return member;
    }

    public MemberResponseDto findMyInfo(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return MemberResponseDto.toMemberResponseDto(member);
    }

    public List<MemberResponseDto> findAll(){
        return memberRepository.findAll().stream()
                .map(a->MemberResponseDto.toMemberResponseDto(a))
                .collect(Collectors.toList());
    }

    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        return MemberResponseDto.toMemberResponseDto(member);
    }

    public MemberResponseDto findByEmail(String email) {
        return MemberResponseDto.toMemberResponseDto(memberRepository.findByEmail(email).orElseThrow());
    }
}
