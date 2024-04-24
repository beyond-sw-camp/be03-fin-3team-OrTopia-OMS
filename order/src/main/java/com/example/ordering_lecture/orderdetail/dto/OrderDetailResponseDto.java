package com.example.ordering_lecture.orderdetail.dto;

import com.example.ordering_lecture.order.dto.OrderResponseDto;
import com.example.ordering_lecture.orderdetail.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponseDto {
    private Long orderingId;
    private Long itemId;
    private int quantity;
    private Long sellerId;
    private int discountPrice;
    private String options;
    private boolean isReviewed;
    private Long id;

    public static OrderDetailResponseDto toDto(OrderDetail orderDetail){
        return OrderDetailResponseDto.builder()
                .id(orderDetail.getId())
                .isReviewed(orderDetail.isReviewed())
                .orderingId(orderDetail.getOrdering().getId())
                .options(orderDetail.getOptions())
                .itemId(orderDetail.getItemId())
                .quantity(orderDetail.getQuantity())
                .sellerId(orderDetail.getSellerId())
                .discountPrice(orderDetail.getDiscountPrice())
                .build();
    }
}
