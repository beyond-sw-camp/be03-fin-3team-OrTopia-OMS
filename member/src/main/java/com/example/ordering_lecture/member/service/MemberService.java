package com.example.ordering_lecture.member.service;

import com.example.ordering_lecture.member.domain.LikedSeller;
import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.domain.Seller;
import com.example.ordering_lecture.member.dto.Buyer.*;
import com.example.ordering_lecture.member.dto.Seller.SellerResponseDto;
import com.example.ordering_lecture.member.repository.LikedSellerRepository;
import com.example.ordering_lecture.member.repository.MemberRepository;
import com.example.ordering_lecture.member.repository.SellerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final SellerRepository sellerRepository;
    private final LikedSellerRepository likedSellerRepository;
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, SellerRepository sellerRepository, LikedSellerRepository likedSellerRepository){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.sellerRepository = sellerRepository;
        this.likedSellerRepository = likedSellerRepository;
    }
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
        memberRequestDto.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        Member member = memberRequestDto.toEntity();
        memberRepository.save(member);
        return MemberResponseDto.toDto(member);
    }
    public MemberResponseDto findById(Long id) {
        //TODO : 에러 코드 추후 수정
        Member member = memberRepository.findById(id).orElseThrow();
        return MemberResponseDto.toDto(member);
    }
    public List<MemberResponseDto> findAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponseDto::toDto)
                .collect(Collectors.toList());
    }
    public List<MemberResponseDto> findMembers(){
        return memberRepository.findByDelYNFalse()
                .stream()
                .map(MemberResponseDto::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public MemberResponseDto updateMember(Long id, MemberUpdateDto memberUpdateDto) {
        //TODO : 에러 코드 추후 수정
        Member member = memberRepository.findById(id).orElseThrow();
        member.updateMember(memberUpdateDto);
        return MemberResponseDto.toDto(member);
    }
    @Transactional
    public void deleteMember(Long id) {
        //TODO : 에러 코드 추후 수정
        Member member = memberRepository.findById(id).orElseThrow();
        member.deleteMember();

        Seller seller = sellerRepository.findById(id).orElseThrow();
        seller.deleteSeller();
    }
    @Transactional
    public Object likeSeller(MemberLikeSellerRequestDto memberLikeSellerRequestDto) {
        //TODO : 에러 코드 추후 수정
        // memberID와 sellerID가 같다면 에러 처리
        Member buyer = memberRepository.findById(memberLikeSellerRequestDto.getBuyerID()).orElseThrow();
        Seller seller = sellerRepository.findById(memberLikeSellerRequestDto.getSellerID()).orElseThrow();
        LikedSeller likedSeller = memberLikeSellerRequestDto.toEntity(buyer, seller);
        likedSellerRepository.save(likedSeller);
        return MemberLikeSellerResponseDto.toDto(likedSeller);
    }

    public Object likeSellers(Long id) {
        return likedSellerRepository.findAllByBuyerId(id)
                .stream()
                .map(likedSeller -> SellerResponseDto.toDto(likedSeller.getSeller()))
                .collect(Collectors.toList());
    }
}
