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
import java.util.List;
import java.util.Optional;
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
    public MemberResponseDto updateMember(String email, MemberUpdateDto memberUpdateDto) throws OrTopiaException{
        Member member = memberRepository.findByEmail(email).orElseThrow(
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
    // 즐겨찾기 추가
    @Transactional
    public MemberLikeSellerResponseDto likeSeller(String email, Long sellerId) throws OrTopiaException {
        Member buyer = memberRepository.findByEmail(email).orElseThrow(
                () -> new OrTopiaException(ErrorCode.NOT_FOUND_MEMBER)
        );
        Seller seller = sellerRepository.findByMemberId(sellerId).orElseThrow(
                () -> new OrTopiaException(ErrorCode.NOT_FOUND_SELLER)
        );
        List<LikedSeller> existingLikes = likedSellerRepository.findByBuyerAndSeller(buyer, seller);
        if (!existingLikes.isEmpty()) {
            throw new OrTopiaException(ErrorCode.ALREADY_LIKED_SELLER);
        }

        LikedSeller likedSeller = LikedSeller.builder()
                .buyer(buyer)
                .seller(seller)
                .build();
        System.out.println(likedSeller.getBuyer().getEmail());
        System.out.println(likedSeller.getSeller().getId());
        likedSellerRepository.save(likedSeller);
        return MemberLikeSellerResponseDto.toDto(likedSeller);
    }
    //구매자가 즐겨찾기한 판매자 목록
    public List<SellerResponseDto> likeSellers(String email) throws OrTopiaException {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new OrTopiaException(ErrorCode.NOT_FOUND_MEMBER)
        );
        List<SellerResponseDto> sellerResponseDtos = likedSellerRepository.findAllByBuyer(member)
                .stream()
                .map(likedSeller -> SellerResponseDto.toDto(likedSeller.getSeller()))
                .collect(Collectors.toList());
        if(sellerResponseDtos.isEmpty()){
            throw new OrTopiaException(ErrorCode.NOT_FOUND_SELLERS);
        }
        return sellerResponseDtos;
    }
    //즐겨찾기 삭제
    @Transactional
    public void unlikeSeller(String buyerEmail, Long sellerId) throws OrTopiaException {
        Member buyer = memberRepository.findByEmail(buyerEmail)
                .orElseThrow(() -> new OrTopiaException(ErrorCode.NOT_FOUND_MEMBER));
        Seller seller = sellerRepository.findByMemberId(sellerId)
                .orElseThrow(() -> new OrTopiaException(ErrorCode.NOT_FOUND_SELLER));
        List<LikedSeller> likedSellers = likedSellerRepository.findByBuyerAndSeller(buyer, seller);
        if (likedSellers.isEmpty()) {
            throw new OrTopiaException(ErrorCode.NOT_FOUND_LIKED_SELLER); // 오류 코드 확인
        }
        likedSellerRepository.deleteAll(likedSellers);
    }
    //판매자를 즐겨찾기한 구매자 목록
    public List<MemberResponseDto> findBuyersByLikedSeller(Long sellerId) throws OrTopiaException {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
                () -> new OrTopiaException(ErrorCode.NOT_FOUND_SELLER)
        );
        List<LikedSeller> likedSellers = likedSellerRepository.findBySeller(seller);
        if (likedSellers.isEmpty()) {
            throw new OrTopiaException(ErrorCode.NOT_FOUND_MEMBER);
        }
        return likedSellers.stream()
                .map(likedSeller -> MemberResponseDto.toDto(likedSeller.getBuyer()))
                .collect(Collectors.toList());
    }
    //즐겨찾기 했는지 안했는지 검증
    public boolean isSellerLikedByBuyer(String email, Long sellerId) throws OrTopiaException {
        Member buyer = memberRepository.findByEmail(email).orElseThrow(
                () -> new OrTopiaException(ErrorCode.NOT_FOUND_MEMBER)
        );
        Seller seller = sellerRepository.findByMemberId(sellerId).orElseThrow(
                () -> new OrTopiaException(ErrorCode.NOT_FOUND_SELLER)
        );
        List<LikedSeller> existingLikes = likedSellerRepository.findByBuyerAndSeller(buyer, seller);
        return !existingLikes.isEmpty();
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

    public String searchNameByEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new OrTopiaException(ErrorCode.NOT_FOUND_MEMBER)
                );
        return member.getName();
    }
}
