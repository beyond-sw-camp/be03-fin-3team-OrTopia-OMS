package com.example.ordering_lecture.member.controller;

import com.example.ordering_lecture.common.OrTopiaResponse;
import com.example.ordering_lecture.member.dto.Seller.*;
import com.example.ordering_lecture.member.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/seller/{email}/create")
    public ResponseEntity<OrTopiaResponse> createSeller(@RequestHeader("myEmail") String email, @RequestBody SellerRequestDto sellerRequestDto){
        SellerResponseDto sellerResponseDto = sellerService.createSeller(email, sellerRequestDto);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("create success", sellerResponseDto);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.CREATED);
    }
    // 판매자 상세조회
    @GetMapping("/seller/{id}")
    public ResponseEntity<OrTopiaResponse> findSeller(@PathVariable Long id){
        SellerResponseDto sellerResponseDto = sellerService.findById(id);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success", sellerResponseDto);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }
    // 판매자 전체조회
    @GetMapping("/seller/All")
    public ResponseEntity<OrTopiaResponse> findAllSellers(){
        List<SellerResponseDto> sellerResponseDtos = sellerService.findAllSellers();
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success", sellerResponseDtos);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }
    // 탈퇴하지 않은 판매자 전체조회
    @GetMapping("/seller/sellers")
    public ResponseEntity<OrTopiaResponse> findSellers(){
        List<SellerResponseDto> sellerResponseDtos = sellerService.findSellers();
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success", sellerResponseDtos);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }
    // 판매자 정보수정
    @PatchMapping("/seller/{id}")
    public ResponseEntity<OrTopiaResponse> updateSeller(@PathVariable Long id, @RequestBody SellerUpdateDto sellerUpdateDto){
        SellerResponseDto sellerResponseDto = sellerService.updateSeller(id, sellerUpdateDto);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("update success", sellerResponseDto);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }
    // 판매자 삭제(delYN=true)
    @DeleteMapping("/seller/{id}")
    public ResponseEntity<OrTopiaResponse> deleteSeller(@PathVariable Long id){
        sellerService.deleteSeller(id);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("delete success");
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }
    // 판매자 Ban
    @PostMapping("/seller/ban/{id}")
    public ResponseEntity<OrTopiaResponse> banSeller(@PathVariable Long id, @RequestBody BannedSellerRequestDto bannedSellerRequestDto){
        BannedSellerResponseDto bannedSellerResponseDto = sellerService.banSeller(id, bannedSellerRequestDto);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("ban success",bannedSellerResponseDto);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }
    // 판매자 Ban 풀기
    @DeleteMapping("/seller/banCancel/{id}")
    public ResponseEntity<OrTopiaResponse> banCancelSeller(@PathVariable Long id){
        sellerService.banCancelSeller(id);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("cancel success");
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }
    // 밴 당한 판매자 목록
    // 밴 취소된 판매자는 제외하고 리턴
    @GetMapping("/seller/bannedSellers")
    public ResponseEntity<OrTopiaResponse> bannedSellers(){
        List<BannedSellerResponseDto> bannedSellerResponseDtos = sellerService.bannedSellers();
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success", bannedSellerResponseDtos);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }
}
