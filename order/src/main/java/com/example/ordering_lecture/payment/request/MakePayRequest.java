package com.example.ordering_lecture.payment.request;

import com.example.ordering_lecture.payment.dto.PayInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

@Component
@RequiredArgsConstructor
public class MakePayRequest {

    public PayRequest getReadyRequest(String email, PayInfoDto payInfoDto){
        LinkedMultiValueMap<String,String> map=new LinkedMultiValueMap<>();
        /** partner_user_id,partner_order_id는 결제 승인 요청에서도 동일해야함 */
        String memberId=email+"";

        String orderId="point"+email;
        // 가맹점 코드 테스트코드는 TC0ONETIME 이다.
        map.add("cid","TC0ONETIME");

        // partner_order_id는 유저 id와 상품명으로 정하였다.
        // 해당내용은 자유롭게 정하시면 됩니다.
        // 중요한점은 다음 결제 승인 정보를 얻을 때
        // 아래 partner_order_id,partner_user_id 가 동일해야 합니다.
        map.add("partner_order_id",orderId);
        map.add("partner_user_id","오토피아");

        // 리액트에서 받아온 payInfoDto로 결제 주문서의 item 이름을
        // 지어주는 과정입니다.
        if(payInfoDto.getItemDtoList().size()==1){
            map.add("item_name", payInfoDto.getItemDtoList().get(0).getName());
        }else{
            map.add("item_name", payInfoDto.getItemDtoList().get(0).getName()+" 포함 " + payInfoDto.getItemDtoList().size() +"개");
        }
        //수량
        map.add("quantity",String.valueOf(payInfoDto.getItemDtoList().size()-1));

        //가격
        map.add("total_amount",payInfoDto.getPrice()+"");

        //비과세 금액
        map.add("tax_free_amount", "0");

        // 아래 url은 사용자가 결제 url에서 결제를 성공, 실패, 취소시
        // redirect할 url로 위에서 설명한 동작 과정에서 5번과 6번 사이 과정에서
        // 나온 결과로 이동할 url을 설정해 주는 것입니다.
        map.add("approval_url", "http://localhost:8080/order-service/payment/success"+"/"+email); // 성공 시 redirect url
        map.add("cancel_url", "http://localhost:8080/order-service/payment/cancel"); // 취소 시 redirect url
        map.add("fail_url", "http://localhost:8080/order-service/payment/fail"); // 실패 시 redirect url

        return new PayRequest("https://kapi.kakao.com/v1/payment/ready",map);
    }

    public PayRequest getApproveRequest(String tid, String email, String pgToken){
        LinkedMultiValueMap<String,String> map=new LinkedMultiValueMap<>();

        String orderId="point"+email;
        // 가맹점 코드 테스트코드는 TC0ONETIME 이다.
        map.add("cid", "TC0ONETIME");

        // getReadyRequest 에서 받아온 tid
        map.add("tid", tid);
        map.add("partner_order_id", orderId); // 주문명
        map.add("partner_user_id", "오토피아");

        // getReadyRequest에서 받아온 redirect url에 클라이언트가
        // 접속하여 결제를 성공시키면 아래의 url로 redirect 되는데
        //http://localhost:8080/payment/success"+"/"+id
        // 여기에 &pg_token= 토큰값 이 붙어서 redirect 된다.
        // 해당 내용을 뽑아 내서 사용하면 된다.
        map.add("pg_token", pgToken);
        return new PayRequest("https://kapi.kakao.com/v1/payment/approve",map);
    }


}
