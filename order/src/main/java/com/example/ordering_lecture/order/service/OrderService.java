package com.example.ordering_lecture.order.service;

import com.example.ordering_lecture.common.CommonResponse;
import com.example.ordering_lecture.order.dto.*;
import com.example.ordering_lecture.order.domain.OrderItem;
import com.example.ordering_lecture.order.domain.Ordering;
import com.example.ordering_lecture.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private final String MEMBER_URI = "http://member-service/";
    private final String ITEM_URI = "http://item-service/";
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final RestTemplate restTemplate;
    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public Ordering create(List<OrderReqDto> orderReqDtos,String email) {
        String memberRequestUri = MEMBER_URI+"member/findByEmail?email="+email;
        MemberDto member = restTemplate.getForObject(memberRequestUri, MemberDto.class);;
        Ordering ordering = Ordering.builder().memberId(member.getId()).build();
        List<ItemQuantityUpdateDto> itemQuantityUpdateDtoList = new ArrayList<>();
        for(OrderReqDto now : orderReqDtos){
            String itemRequestUri = ITEM_URI+"item/"+now.getItemId();
            ResponseEntity<ItemDto> responseEntity =  restTemplate.getForEntity(itemRequestUri, ItemDto.class);
            ItemDto item = responseEntity.getBody();
            System.out.println(item.getCategory());
            System.out.println(item.getStockQuantity());
            OrderItem orderItem = OrderItem.builder()
                    .itemId(item.getId())
                    .quantity(now.getCount())
                    .ordering(ordering)
                    .build();
            ordering.getOrderItemList().add(orderItem);
            if(item.getStockQuantity() < orderItem.getQuantity()){
                throw new IllegalArgumentException("아이템의 갯수보다 많이 주문할 수 없습니다.");
            }
            int newQuantity = (int) (item.getStockQuantity()-now.getCount());
            ItemQuantityUpdateDto itemQuantityUpdateDto = new ItemQuantityUpdateDto();
            itemQuantityUpdateDto.setId(item.getId());
            itemQuantityUpdateDto.setStockQuantity(newQuantity);
            itemQuantityUpdateDtoList.add(itemQuantityUpdateDto);
        }
        Ordering ordering1 = orderRepository.save(ordering);
        // 외부 API 호출
        // orderRepo save를 먼저 함으로, 위 코드에서 에러 발생시 item 서비스 호출하지 않으므로,
        // 트랜잭션 문제 발생하지  않음
        String itemPatchUri = ITEM_URI+"item/updateQuantity";
        HttpEntity<List<ItemQuantityUpdateDto>> entity = new HttpEntity<>(itemQuantityUpdateDtoList);
        ResponseEntity<CommonResponse> commonResponseResponseEntity = restTemplate
                .exchange(itemPatchUri,HttpMethod.POST,entity, CommonResponse.class);
        // 만일 외부 API 호출 이후, 추가적인 로직이 불가피하게 존재할 경우 트랜잭션 이슈는 여전히 발생할 수 있다.
        // 1. 두 서비스간의 트랜잭션을 묶어주거나.
        // 2. try catch 로  묶어서 롤백하는 API를 구현 후 실행하여 수동으로 트랜잭션을 수행합니다.
        return ordering1;
    }

    public Ordering cancel(Long id){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        Ordering ordering =orderRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("없는 주문 입니다."));
//        if(ordering.getOrderStatus().equals(OrderStatus.CANCELED)){
//            throw new IllegalArgumentException("이미 취소된 주문입니다.");
//        }
//
//        if(ordering.getMember().getEmail().equals(email) || authentication.getAuthorities().contains((new SimpleGrantedAuthority("ROLE_ADMIN")))){
//            ordering.cancelOder();
//            for(OrderItem now : ordering.getOrderItemList()){
//                Item item = itemRepository.findById(now.getItem().getId()).orElseThrow(()-> new EntityNotFoundException("해당 아이템이 없습니다"));
//                item.updateStockQuantity((-1*now.getQuantity().intValue()));
//            }
//            return ordering;
//        }
//        throw new AccessDeniedException("주문을 취소할 권한이 없습니다.");
        return null;
    }

    public List<OrderResDto> findAll() {
        List<OrderResDto> orderResDtos = new ArrayList<>();
        return orderRepository.findAll().stream().map(a->OrderResDto.toDto(a)).collect(Collectors.toList());
    }

    public List<OrderResDto> findMyOrder(Long memberId) {
        return orderRepository.findByMemberId(memberId)
                .stream()
                .map(OrderResDto::toDto)
                .collect(Collectors.toList());
    }
}
