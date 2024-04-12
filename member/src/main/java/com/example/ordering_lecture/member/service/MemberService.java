package com.example.ordering_lecture.member.service;


import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import com.example.ordering_lecture.common.MemberLoginReqDto;
import com.example.ordering_lecture.common.MemberLoginResDto;
import com.example.ordering_lecture.member.domain.LikedSeller;
import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.domain.Seller;
import com.example.ordering_lecture.member.dto.Buyer.*;
import com.example.ordering_lecture.member.dto.Seller.SellerResponseDto;
import com.example.ordering_lecture.member.repository.LikedSellerRepository;
import com.example.ordering_lecture.member.repository.MemberRepository;
import com.example.ordering_lecture.member.repository.SellerRepository;
import com.example.ordering_lecture.securities.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final SellerRepository sellerRepository;
    private final LikedSellerRepository likedSellerRepository;
    private final JwtTokenProvider jwtTokenProvider;
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, SellerRepository sellerRepository, LikedSellerRepository likedSellerRepository, JwtTokenProvider jwtTokenProvider){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.sellerRepository = sellerRepository;
        this.likedSellerRepository = likedSellerRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) throws OrTopiaException{
        memberRequestDto.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        Member member = memberRequestDto.toEntity();
        memberRepository.save(member);
        return MemberResponseDto.toDto(member);
    }
    public MemberResponseDto readMember(String email) throws OrTopiaException {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new OrTopiaException(ErrorCode.NOT_FOUND_MEMBER)
                );
        return MemberResponseDto.toDto(member);
    }
    public List<MemberResponseDto> findAllMembers() throws OrTopiaException {
        List<MemberResponseDto> memberResponseDtos = memberRepository.findAll()
                .stream()
                .map(MemberResponseDto::toDto)
                .collect(Collectors.toList());
        if(memberResponseDtos.isEmpty()){
            throw new OrTopiaException(ErrorCode.NOT_FOUND_MEMBERS);
        }
        return memberResponseDtos;
    }
    public List<MemberResponseDto> findMembers() throws OrTopiaException{
        List<MemberResponseDto> memberResponseDtos = memberRepository.findByDelYNFalse()
                .stream()
                .map(MemberResponseDto::toDto)
                .collect(Collectors.toList());
        if(memberResponseDtos.isEmpty()){
            throw new OrTopiaException(ErrorCode.NOT_FOUND_MEMBERS);
        }
        return memberResponseDtos;
    }
    @Transactional
    public MemberResponseDto updateMember(Long id, MemberUpdateDto memberUpdateDto) throws OrTopiaException{
        Member member = memberRepository.findById(id).orElseThrow(
                ()-> new OrTopiaException(ErrorCode.NOT_FOUND_MEMBER)
                );
        member.updateMember(memberUpdateDto);
        return MemberResponseDto.toDto(member);
    }
    @Transactional
    public void deleteMember(Long id) throws OrTopiaException{
        Member member = memberRepository.findById(id).orElseThrow(
                ()-> new OrTopiaException(ErrorCode.NOT_FOUND_MEMBER)
                );
        member.deleteMember();
        Seller seller = sellerRepository.findById(id).orElseThrow(
                ()-> new OrTopiaException(ErrorCode.NOT_FOUND_SELLER)
                );
        seller.deleteSeller();
    }
    @Transactional
    public MemberLikeSellerResponseDto likeSeller(MemberLikeSellerRequestDto memberLikeSellerRequestDto)  throws OrTopiaException  {
        //TODO : 에러 코드 추후 수정
        // memberID와 sellerID가 같다면 에러 처리
        Member buyer = memberRepository.findById(memberLikeSellerRequestDto.getBuyerID()).orElseThrow(
                ()-> new OrTopiaException(ErrorCode.NOT_FOUND_MEMBER)
                );
        Seller seller = sellerRepository.findById(memberLikeSellerRequestDto.getSellerID()).orElseThrow(
                ()-> new OrTopiaException(ErrorCode.NOT_FOUND_SELLER)
                );
        LikedSeller likedSeller = memberLikeSellerRequestDto.toEntity(buyer, seller);
        likedSellerRepository.save(likedSeller);
        return MemberLikeSellerResponseDto.toDto(likedSeller);
    }

    public List<SellerResponseDto> likeSellers(Long id) throws OrTopiaException {
        List<SellerResponseDto> sellerResponseDtos = likedSellerRepository.findAllByBuyerId(id)
                .stream()
                .map(likedSeller -> SellerResponseDto.toDto(likedSeller.getSeller()))
                .collect(Collectors.toList());
        if(sellerResponseDtos.isEmpty()){
            throw new OrTopiaException(ErrorCode.NOT_FOUND_SELLERS);
        }
        return sellerResponseDtos;
    }

    public MemberLoginResDto loginService(MemberLoginReqDto memberLoginReqDto) {
        // 이메일을 기준으로 사용자 조회
        Member member = memberRepository.findByEmail(memberLoginReqDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일을 찾지 못했습니다. " + memberLoginReqDto.getEmail()));
        if (!passwordEncoder.matches(memberLoginReqDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("유효하지 않은 이메일 입니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getRole().toString());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail(), member.getRole().toString());

        return new MemberLoginResDto(member.getId(),accessToken,refreshToken);
    }

    public MemberResponseDto findIdByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일을 찾지 못했습니다. " + email));
        return MemberResponseDto.toDto(member);
    }
}
