package com.example.ordering_lecture.item.controller;

import com.example.ordering_lecture.common.OrTopiaResponse;
import com.example.ordering_lecture.item.dto.*;
import com.example.ordering_lecture.item.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrTopiaResponse> createItem(@Valid @ModelAttribute ItemRequestDto itemRequestDto, @RequestPart(name="optionList")List<OptionRequestDto> optionRequestDtos, @RequestHeader("myEmail") String email){
        ItemResponseDto itemResponseDto = itemService.createItem(itemRequestDto,optionRequestDtos,email);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("create success",itemResponseDto);
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.CREATED);
    }

    @GetMapping("/items")
    public ResponseEntity<OrTopiaResponse> showAllItems(){
        List<ItemResponseDto> itemResponseDtos = itemService.showAllItem();
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success",itemResponseDtos);
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }
    @GetMapping("read/{id}")
    public ResponseEntity<OrTopiaResponse> readItem(@PathVariable Long id,@RequestHeader("myEmail") String email){
        ItemResponseDto itemResponseDto = itemService.readItem(id,email);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success",itemResponseDto);
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }
    @GetMapping("/recent_items")
    public ResponseEntity<OrTopiaResponse> readRecentItems(@RequestHeader("myEmail") String email){
        List<ItemResponseDto> itemResponseDtos = itemService.readRecentItems(email);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success",itemResponseDtos);
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }
    @GetMapping("/find_item_email")
    public ResponseEntity<OrTopiaResponse> findItemById(@RequestHeader("myEmail") String email){
        List<ItemResponseDto> itemResponseDtos = itemService.findItemByEmail(email);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success",itemResponseDtos);
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }
  
    @PatchMapping("/update_item/{id}")
    public ResponseEntity<OrTopiaResponse> updateItem(@PathVariable Long id,@ModelAttribute ItemUpdateDto itemUpdateDto){
        ItemResponseDto itemResponseDto = itemService.updateItem(id,itemUpdateDto);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("update success",itemResponseDto);
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<OrTopiaResponse> deleteItem(@PathVariable Long id){
        itemService.deleteItem(id);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("delete success",null);
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }

    @PatchMapping("/ban_items/{sellerId}")
    public ResponseEntity<OrTopiaResponse> banItems(@PathVariable Long sellerId){
        List<ItemResponseDto> itemResponseDtos = itemService.banItem(sellerId);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("ban success",itemResponseDtos);
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }
  
    @PatchMapping("/ban_canceled_items/{sellerId}")
    public ResponseEntity<OrTopiaResponse> banCanceled(@PathVariable Long sellerId){
        List<ItemResponseDto> itemResponseDtos = itemService.releaseBanItem(sellerId);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("ban canceled success",itemResponseDtos);
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }

    @GetMapping("/{id}/imagePath")
    public String getImagePath(@PathVariable Long id){
        return itemService.getImagePath(id);
    }

    // 추천 아이템의 이미지와 id를 가져오는 api
    @GetMapping("/recommendItems")
    public ResponseEntity<OrTopiaResponse> findRecommendItem(@RequestHeader("myEmail") String email){
        List<RecommendationRedisData> recommendationRedisDatas = itemService.findRecommendItem(email);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success",recommendationRedisDatas);
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }


    @GetMapping("read/{id}/my_page")
    public ResponseEntity<OrTopiaResponse> readItemForMyPage(@PathVariable Long id,@RequestHeader("myEmail") String email) {
        ItemResponseDto itemResponseDto = itemService.readItemForMyPage(id, email);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success", itemResponseDto);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }
    // 조건에 맞는 item의 itemOptionQuantityId를 찾아오는 api
    @PostMapping("/search/optionDetailId/{itemId}")
    public Long searchIdByOptionDetail(@PathVariable Long itemId,@RequestBody List<String> values){
       return itemService.searchIdByOptionDetail(itemId,values);
    }
}
