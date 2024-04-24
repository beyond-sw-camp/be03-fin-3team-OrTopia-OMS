package com.example.ordering_lecture.orderdetail.dto;

import com.example.ordering_lecture.order.entity.Ordering;
import com.example.ordering_lecture.orderdetail.entity.OrderDetail;
import com.example.ordering_lecture.payment.dto.ItemOptionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequestDto {
    private Ordering ordering;
    private Long id;
    private int count;
    private Long sellerId;
    private List<ItemOptionDto> options;
    public OrderDetail toEntity(Ordering ordering,String options){
        return OrderDetail.builder()
                .ordering(ordering)
                .options(options)
                .itemId(this.id)
                .sellerId(this.sellerId)
                .quantity(this.count)
                .build();
    }
}
