package com.example.ordering_lecture.payment.service;

import com.example.ordering_lecture.payment.dto.PayApproveResDto;
import com.example.ordering_lecture.payment.dto.PayInfoDto;
import com.example.ordering_lecture.payment.dto.PayReadyResDto;
import com.example.ordering_lecture.payment.request.MakePayRequest;
import com.example.ordering_lecture.payment.request.PayRequest;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
public class PaymentService {

    private final MakePayRequest makePayRequest;
//    private final MemberRepository memberRepository;

    @Value("${pay.admin-key}")
    private String adminKey;

    private String tmpTid;
    public PaymentService(MakePayRequest makePayRequest) {
        this.makePayRequest = makePayRequest;
    }


    /** 카오페이 결제를 시작하기 위해 상세 정보를 카카오페이 서버에 전달하고 결제 고유 번호(TID)를 받는 단계입니다.
     * 어드민 키를 헤더에 담아 파라미터 값들과 함께 POST로 요청합니다.
     * 테스트  가맹점 코드로 'TC0ONETIME'를 사용 */
    @Transactional
    public PayReadyResDto getRedirectUrl(Long id,PayInfoDto payInfoDto)throws Exception{
//        Member member=memberRepository.findByEmail(name)
//                .orElseThrow(()-> new Exception("해당 유저가 존재하지 않습니다."));
        HttpHeaders headers=new HttpHeaders();
        /** 요청 헤더 */
        String auth = "KakaoAK " + adminKey;
        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization",auth);
        /** 요청 Body */
        PayRequest payRequest=makePayRequest.getReadyRequest(id,payInfoDto);
        /** Header와 Body 합쳐서 RestTemplate로 보내기 위한 밑작업 */
        HttpEntity<MultiValueMap<String, String>> urlRequest = new HttpEntity<>(payRequest.getMap(), headers);
        /** RestTemplate로 Response 받아와서 DTO로 변환후 return */
        RestTemplate rt = new RestTemplate();
        PayReadyResDto payReadyResDto = rt.postForObject(payRequest.getUrl(), urlRequest, PayReadyResDto.class);
        tmpTid = payReadyResDto.getTid();
//        member.updateTid(payReadyResDto.getTid());
        return payReadyResDto;
    }

    @Transactional
    public PayApproveResDto getApprove(String pgToken, Long id)throws Exception{
//        Member member=memberRepository.findById(id)
//                .orElseThrow(()->new Exception("해당 유저가 존재하지 않습니다."));
//        String tid=member.getTid();
        String tid=tmpTid;
        HttpHeaders headers=new HttpHeaders();
        String auth = "KakaoAK " + adminKey;
        /** 요청 헤더 */
        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization",auth);
        /** 요청 Body */
        PayRequest payRequest=makePayRequest.getApproveRequest(tid,id,pgToken);
        /** Header와 Body 합쳐서 RestTemplate로 보내기 위한 밑작업 */
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(payRequest.getMap(), headers);
        // 요청 보내기
        RestTemplate rt = new RestTemplate();
        PayApproveResDto payApproveResDto = rt.postForObject(payRequest.getUrl(), requestEntity, PayApproveResDto.class);
        return payApproveResDto;
    }
}
