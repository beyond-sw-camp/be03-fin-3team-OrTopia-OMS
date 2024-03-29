package com.example.ordering_lecture.member.service;

import com.example.ordering_lecture.member.domain.BannedSeller;
import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.domain.Seller;
import com.example.ordering_lecture.member.dto.Seller.*;
import com.example.ordering_lecture.member.repository.BannedSellerRepository;
import com.example.ordering_lecture.member.repository.MemberRepository;
import com.example.ordering_lecture.member.repository.SellerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;
    private final MemberRepository memberRepository;
    private final BannedSellerRepository bannedSellerRepository;

    public SellerService(SellerRepository sellerRepository, MemberRepository memberRepository, BannedSellerRepository bannedSellerRepository) {
        this.sellerRepository = sellerRepository;
        this.memberRepository = memberRepository;
        this.bannedSellerRepository = bannedSellerRepository;
    }

    public SellerResponseDto createSeller(Long id, SellerRequestDto sellerRequestDto) {
        Member member = memberRepository.findById(id).orElseThrow();
        Seller seller = sellerRequestDto.toEntity(member);
        sellerRepository.save(seller);
        member.updateRoleToSeller();
        return SellerResponseDto.toDto(seller);
    }

    public Object findById(Long id) {
        //TODO : 에러 코드 추후 수정
        Seller seller = sellerRepository.findById(id).orElseThrow();
        return SellerResponseDto.toDto(seller);
    }
    // 판매자 전체 조회
    public List<SellerResponseDto> findAllSellers() {
        return sellerRepository.findAll()
                .stream()
                .map(SellerResponseDto::toDto)
                .collect(Collectors.toList());
    }
    // 탈퇴하지 않은 판매자 전체 조회
    public List<SellerResponseDto> findSellers() {
        return sellerRepository.findByDelYNFalse()
               .stream()
               .map(SellerResponseDto::toDto)
               .collect(Collectors.toList());
    }
    @Transactional
    public Object updateSeller(Long id, SellerUpdateDto sellerUpdateDto) {
        //TODO : 에러 코드 추후 수정
        Seller seller = sellerRepository.findById(id).orElseThrow();
        seller.updateSeller(sellerUpdateDto);
        return SellerResponseDto.toDto(seller);
    }

    @Transactional
    public void deleteSeller(Long id) {
        //TODO : 에러 코드 추후 수정
        Seller seller = sellerRepository.findById(id).orElseThrow();
        seller.deleteSeller();
    }

    public Object banSeller(Long id, BannedSellerRequestDto bannedSellerRequestDto) {
        //TODO : 에러 코드 추후 수정
        Seller seller = sellerRepository.findById(id).orElseThrow();
        BannedSeller bannedSeller = bannedSellerRequestDto.toEntity(seller);

        bannedSellerRepository.save(bannedSeller);
        return BannedSellerResponseDto.toDto(bannedSeller);
    }

    @Transactional
    public void banCancelSeller(Long id) {
        //TODO : 에러 코드 추후 수정
        BannedSeller bannedSeller = bannedSellerRepository.findById(id).orElseThrow();
        bannedSeller.banCancel();
    }

    public Object bannedSellers() {
        return bannedSellerRepository.findAllByDelYNFalse()
                .stream()
                .map(BannedSellerResponseDto::toDto)
                .collect(Collectors.toList());
    }
}
