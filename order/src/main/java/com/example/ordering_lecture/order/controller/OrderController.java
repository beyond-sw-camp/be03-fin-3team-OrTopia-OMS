package com.example.ordering_lecture.order.controller;

import com.example.ordering_lecture.common.CommonResponse;
import com.example.ordering_lecture.order.domain.Ordering;
import com.example.ordering_lecture.order.dto.OrderReqDto;
import com.example.ordering_lecture.order.dto.OrderResDto;
import com.example.ordering_lecture.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/create")
    public ResponseEntity<CommonResponse> orderCreate(@RequestBody List<OrderReqDto> orderReqDtos,@RequestHeader("myEmail") String email){
        Ordering ordering = orderService.create(orderReqDtos,email);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED,"order success",ordering.getOrderStatus()),HttpStatus.CREATED);
    }

    @DeleteMapping("/order/{id}/cancel")
    public ResponseEntity<CommonResponse> cancelOrder(@PathVariable Long id){
        Ordering ordering = orderService.cancel(id);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK,"order canceled",ordering.getOrderStatus()),HttpStatus.OK);
    }

    @GetMapping("/orders")
    public List<OrderResDto> orderList(){
        return orderService.findAll();
    }
}
