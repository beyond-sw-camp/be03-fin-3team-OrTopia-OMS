package com.example.ordering_lecture.order.service;

import com.example.ordering_lecture.order.dto.OrderRequestDto;
import com.example.ordering_lecture.order.dto.OrderResponseDto;
import com.example.ordering_lecture.order.dto.OrderUpdateDto;
import com.example.ordering_lecture.order.entity.Ordering;
import com.example.ordering_lecture.order.repository.OrderRepository;
import com.example.ordering_lecture.orderdetail.dto.OrderDetailRequestDto;
import com.example.ordering_lecture.orderdetail.dto.OrderDetailResponseDto;
import com.example.ordering_lecture.orderdetail.entity.OrderDetail;
import com.example.ordering_lecture.orderdetail.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderingService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderingService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Transactional
    // 더티 체킹 설정
    public Ordering createOrder(OrderRequestDto orderRequestDto) {
        Ordering ordering = orderRequestDto.toEntity();
        orderRepository.save(ordering);
        for(OrderDetailRequestDto orderDetailRequestDto:orderRequestDto.getOrderDetailRequestDtoList()){
            OrderDetail orderDetail = orderDetailRequestDto.toEntity(ordering);
            orderDetailRepository.save(orderDetail);
        }
        return ordering;
    }

    public Object updateOrder(OrderUpdateDto orderUpdateDto) {
        Ordering ordering = orderRepository.findById(orderUpdateDto.getId()).orElseThrow();
        ordering.updateStatue(orderUpdateDto.getStatue());
        return ordering;
    }

    public Object showAllOrder() {
        List<Ordering> orderings = orderRepository.findAll();
        return orderings.stream()
                .map(OrderResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public Object showAllOrdersDetail() {
        List<Ordering> orderings = orderRepository.findAll();
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for(Ordering ordering : orderings){
            OrderResponseDto orderResponseDto = OrderResponseDto.toDto(ordering);
            List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderingId(ordering.getId());
            List<OrderDetailResponseDto> orderDetailResponseDtoList = orderDetails.stream()
                    .map(OrderDetailResponseDto::toDto)
                    .collect(Collectors.toList());
            orderResponseDto.setOrderDetailResponseDtoList(orderDetailResponseDtoList);
            orderResponseDtos.add(orderResponseDto);
        }
        return orderResponseDtos;
    }

    public Object showMyOrders(String email) {
        List<Ordering> orderings = orderRepository.findAllByEmail(email);
        return orderings.stream()
                .map(OrderResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public Object showMyOrdersDetail(String email) {
        List<Ordering> orderings = orderRepository.findAllByEmail(email);
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for(Ordering ordering : orderings){
            OrderResponseDto orderResponseDto = OrderResponseDto.toDto(ordering);
            List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderingId(ordering.getId());
            List<OrderDetailResponseDto> orderDetailResponseDtoList = orderDetails.stream()
                    .map(OrderDetailResponseDto::toDto)
                    .collect(Collectors.toList());
            orderResponseDto.setOrderDetailResponseDtoList(orderDetailResponseDtoList);
            orderResponseDtos.add(orderResponseDto);
        }
        return orderResponseDtos;
    }
}
