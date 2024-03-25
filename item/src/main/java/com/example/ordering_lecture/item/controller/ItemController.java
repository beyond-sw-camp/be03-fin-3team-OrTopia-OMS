package com.example.ordering_lecture.item.controller;

import com.example.ordering_lecture.common.CommonResponse;
import com.example.ordering_lecture.item.domain.Item;
import com.example.ordering_lecture.item.dto.ItemReqDto;
import com.example.ordering_lecture.item.dto.ItemResDto;
import com.example.ordering_lecture.item.dto.ItemSearchDto;
import com.example.ordering_lecture.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class ItemController {
    @Autowired
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //TODO : Need Valid Check code
    @PostMapping("item/create")
    public ResponseEntity<CommonResponse> itemCreate(ItemReqDto itemReqDto){
        Item item = itemService.create(itemReqDto);
        return new ResponseEntity<>(
                new CommonResponse(
                        HttpStatus.CREATED, "item successfully created",item.getId()),
                HttpStatus.CREATED);
    }

    @GetMapping("items")
    public ResponseEntity<List<ItemResDto>> items(ItemSearchDto itemSearchDto, Pageable pageable){
        List<ItemResDto> itemResDtos = itemService.findAll(itemSearchDto,pageable);
        return new ResponseEntity<>(itemResDtos,HttpStatus.OK);
    }
    @GetMapping("item/{id}/image")
    public ResponseEntity<Resource> getImage(@PathVariable Long id){
        Resource resource = itemService.getImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(resource,headers,HttpStatus.OK);
    }
    @PatchMapping("/item/{id}/update")
    public ResponseEntity<CommonResponse> itemUpdate(@PathVariable Long id, ItemReqDto itemReqDto){
        Item item = itemService.update(id,itemReqDto);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK,"Item successfully update",null),HttpStatus.OK);
    }
    @DeleteMapping("/item/{id}/delete")
    public ResponseEntity<CommonResponse> itemDelete(@PathVariable Long id, ItemReqDto itemReqDto){
        Item item  = itemService.delete(id);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK,"item successfully delete",item.getId()),HttpStatus.OK);
    }
}
