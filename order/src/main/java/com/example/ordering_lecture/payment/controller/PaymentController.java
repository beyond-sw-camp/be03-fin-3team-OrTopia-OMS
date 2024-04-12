package com.example.ordering_lecture.payment.controller;

import com.example.ordering_lecture.common.OrTopiaResponse;
import com.example.ordering_lecture.payment.dto.PayApproveResDto;
import com.example.ordering_lecture.payment.dto.PayInfoDto;
import com.example.ordering_lecture.payment.dto.PayReadyResDto;
import com.example.ordering_lecture.payment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /** 결제 준비 redirect url 받기 --> 상품명과 가격을 같이 보내줘야함 */
    @GetMapping("/ready")
    public ResponseEntity<Object> getRedirectUrl(@RequestBody PayInfoDto payInfoDto) {
        try {
            PayReadyResDto payReadyResDto =  paymentService.getRedirectUrl(1L,payInfoDto);
            OrTopiaResponse orTopiaResponse = new OrTopiaResponse("success",payReadyResDto);
            return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);

        }
        catch(Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * 결제 성공 pid 를  받기 위해 request를 받고 pgToken은 rediret url에 뒤에 붙어오는걸 떼서 쓰기 위함
     */
    @GetMapping("/success/{id}")
    public ResponseEntity<Object> afterGetRedirectUrl(@PathVariable("id")Long id,
                                                 @RequestParam("pg_token") String pgToken) {
        try {
            PayApproveResDto kakaoApprove = paymentService.getApprove(pgToken,id);
            OrTopiaResponse orTopiaResponse = new OrTopiaResponse("success",kakaoApprove);
            return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public ResponseEntity<Object> cancel() {
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("사용자가 결제를 취소했습니다");
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }

    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public ResponseEntity<Object> fail() {
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("결제 실패");
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }
}
