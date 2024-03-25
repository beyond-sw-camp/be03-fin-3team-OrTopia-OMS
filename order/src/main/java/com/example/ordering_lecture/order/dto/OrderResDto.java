package com.example.ordering_lecture.order.dto;

import com.example.ordering_lecture.order.domain.Ordering;
import com.example.ordering_lecture.order.domain.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResDto {
    private Long id;
//    private String email;
    private String orderStatus;
    private LocalDateTime createdTime;
    private List<OrderResItemDto> orderItems;

    @Data
    public static class OrderResItemDto{
        private Long id;
        private String itemName;
        private int count;
    }

    public static OrderResDto toDto(Ordering ordering){
        OrderResDto orderResDto = new OrderResDto();
        orderResDto.setId(ordering.getId());
        orderResDto.setOrderStatus(ordering.getOrderStatus().toString());
//        orderResDto.setEmail(ordering.getMember().getEmail());
        orderResDto.setCreatedTime(ordering.getCreatedTime());
        orderResDto.setOrderItems(new ArrayList<>());
        for(OrderItem orderItem : ordering.getOrderItemList()){
            OrderResItemDto orderResItemDto = new OrderResItemDto();
            orderResItemDto.setId(orderItem.getId());
//            orderResItemDto.setItemName(orderItem.getItem().getName());
            orderResItemDto.setCount(orderItem.getQuantity().intValue());
            orderResDto.getOrderItems().add(orderResItemDto);
        }
        return orderResDto;
    }
}
