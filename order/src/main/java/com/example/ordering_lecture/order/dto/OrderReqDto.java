package com.example.ordering_lecture.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderReqDto {
//    private List<OrderReqItemDto> orderReqDtos;
    private Long itemId;
    private Long count;


//    //TODO: 보내주는 데이터의 구조가 좀 지저분하다.
//    // 해결방법은 ?
//    @Data
//    public static class OrderReqItemDto{
//        private Long itemId;
//        private Long count;
//    }
}


//