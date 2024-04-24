package com.example.ordering_lecture.payment.service;

import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import com.example.ordering_lecture.payment.controller.ItemServiceClient;
import com.example.ordering_lecture.payment.dto.*;
import com.example.ordering_lecture.payment.request.MakePayRequest;
import com.example.ordering_lecture.payment.request.PayRequest;
import com.example.ordering_lecture.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final MakePayRequest makePayRequest;
    private final RedisService redisService;
    private final ItemServiceClient itemServiceClient;

    @Value("${pay.admin-key}")
    private String adminKey;

    /** 카오페이 결제를 시작하기 위해 상세 정보를 카카오페이 서버에 전달하고 결제 고유 번호(TID)를 받는 단계입니다.
     * 어드민 키를 헤더에 담아 파라미터 값들과 함께 POST로 요청합니다.
     * 테스트  가맹점 코드로 'TC0ONETIME'를 사용 */
    @Transactional
    public PayReadyResDto getRedirectUrl(String email,PayInfoDto payInfoDto){
        HttpHeaders headers=new HttpHeaders();
        //아이템의 재고를 레디스로 확인
        for(ItemDto itemDto:payInfoDto.getItemDtoList()){
            Long id = null;
            //옵션이 없는 경우 체킹
            if(!itemDto.getOptions().isEmpty()) {
                List<String> values = new ArrayList<>();
                for (ItemOptionDto itemOptionDto : itemDto.getOptions()) {
                    values.add(itemOptionDto.getValue());
                }
                //value와 Item 아이디를 가지고 ItemOptionDetailQuantity 를 찾아와야해.
                id = itemServiceClient.searchIdByOptionDetail(itemDto.getId(), values);
            }else{
                List<String> values = new ArrayList<>();
                id = itemServiceClient.searchIdByOptionDetail(itemDto.getId(), values);
            }
            //찾아온 ItemOptionDetailQuantity의 id를 가지고 redis에서 재고를 조회
            int nowStock = redisService.getValuesItemCount(id);
            if (nowStock - itemDto.getCount() < 0) {
                throw new OrTopiaException(ErrorCode.ITEM_QUANTITY_ERROR);
            }
            // redis내 재고를 업데이트
            redisService.setItemQuantity(id, nowStock - itemDto.getCount());
        }
        /** 요청 헤더 */
        String auth = "KakaoAK " + adminKey;
        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization",auth);
        /** 요청 Body */
        PayRequest payRequest=makePayRequest.getReadyRequest(email,payInfoDto);
        /** Header와 Body 합쳐서 RestTemplate로 보내기 위한 밑작업 */
        HttpEntity<MultiValueMap<String, String>> urlRequest = new HttpEntity<>(payRequest.getMap(), headers);
        /** RestTemplate로 Response 받아와서 DTO로 변환후 return */
        RestTemplate rt = new RestTemplate();
        PayReadyResDto payReadyResDto = rt.postForObject(payRequest.getUrl(), urlRequest, PayReadyResDto.class);
        // redis에 Tid 값을 저장
        redisService.setValues(email,payReadyResDto.getTid());
        return payReadyResDto;
    }

    @Transactional
    public PayApproveResDto getApprove(String pgToken, String email){
        // 레디스에 저장된 tid 값을 불러옴
        String tid=redisService.getValues(email);
        HttpHeaders headers=new HttpHeaders();
        String auth = "KakaoAK " + adminKey;
        /** 요청 헤더 */
        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization",auth);
        /** 요청 Body */
        PayRequest payRequest=makePayRequest.getApproveRequest(tid,email,pgToken);
        /** Header와 Body 합쳐서 RestTemplate로 보내기 위한 밑작업 */
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(payRequest.getMap(), headers);
        RestTemplate rt = new RestTemplate();
        PayApproveResDto payApproveResDto = rt.postForObject(payRequest.getUrl(), requestEntity, PayApproveResDto.class);
        //TODO: order를 생성
        return payApproveResDto;
    }
}
