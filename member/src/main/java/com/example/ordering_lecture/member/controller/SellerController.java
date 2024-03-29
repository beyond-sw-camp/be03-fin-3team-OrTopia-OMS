package com.example.ordering_lecture.member.controller;

import com.example.ordering_lecture.member.dto.Seller.BannedSellerRequestDto;
import com.example.ordering_lecture.member.dto.Seller.SellerRequestDto;
import com.example.ordering_lecture.member.dto.Seller.SellerResponseDto;
import com.example.ordering_lecture.member.dto.Seller.SellerUpdateDto;
import com.example.ordering_lecture.member.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {
    @Autowired
    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    /*
    SELLER 관련 API
    */
    // 판매자 등록 : id번 사용자의 판매자 등록 요청
    @PostMapping("/seller/{id}/create")
    public Object createSeller(@PathVariable Long id, @RequestBody SellerRequestDto sellerRequestDto){
        SellerResponseDto sellerResponseDto = sellerService.createSeller(id, sellerRequestDto);
        return sellerResponseDto;
    }
    // 판매자 상세조회
    @GetMapping("/seller/{id}")
    public Object findSeller(@PathVariable Long id){
        return sellerService.findById(id);
    }

    // 판매자 전체조회
    @GetMapping("/seller/All")
    public Object findAllSellers(){
        List<SellerResponseDto> sellerResponseDtos = sellerService.findAllSellers();
        return sellerResponseDtos;
    }
    // 탈퇴하지 않은 판매자 전체조회
    @GetMapping("/seller/sellers")
    public Object findSellers(){
        List<SellerResponseDto> sellerResponseDtos = sellerService.findSellers();
        return sellerResponseDtos;
    }
    // 판매자 정보수정
    @PatchMapping("/seller/{id}")
    public Object updateSeller(@PathVariable Long id, @RequestBody SellerUpdateDto sellerUpdateDto){
        return sellerService.updateSeller(id, sellerUpdateDto);
    }
    // 판매자 삭제(delYN=true)
    @DeleteMapping("/seller/{id}")
    public String deleteSeller(@PathVariable Long id){
        sellerService.deleteSeller(id);
        return "ok";
    }
    // 판매자 Ban
    @PostMapping("/seller/ban/{id}")
    public String banSeller(@PathVariable Long id, @RequestBody BannedSellerRequestDto bannedSellerRequestDto){
        sellerService.banSeller(id, bannedSellerRequestDto);
        return "ok";
    }
    // 판매자 Ban 풀기
    @DeleteMapping("/seller/banCancel/{id}")
    public String banCancelSeller(@PathVariable Long id){
        sellerService.banCancelSeller(id);
        return "ok";
    }
    // 밴 당한 판매자 목록
    // 밴 취소된 판매자는 제외하고 리턴
    @GetMapping("/seller/bannedSellers")
    public Object bannedSellers(){
        return sellerService.bannedSellers();
    }
}
