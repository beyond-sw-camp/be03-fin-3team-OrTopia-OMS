package com.example.ordering_lecture.item.controller;

import com.example.ordering_lecture.item.dto.ItemRequestDto;
import com.example.ordering_lecture.item.dto.ItemUpdateDto;
import com.example.ordering_lecture.item.service.ItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item_server")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/create")
    public Object createItem(@ModelAttribute ItemRequestDto itemRequestDto){
        itemService.createItem(itemRequestDto);
        return null;
    }

    @GetMapping("/items")
    public Object showAllItems(){
        itemService.showAllItem();
        return null;
    }
    @GetMapping("/find_item_email/{sellerEmail}")
    public Object findItemByEmail(@PathVariable String sellerEmail){
        itemService.findItemByEmail(sellerEmail);
        return null;
    }
    @PatchMapping("/update_item/{id}")
    public Object updateItem(@PathVariable Long id,@ModelAttribute ItemUpdateDto itemUpdateDto){
        itemService.updateItem(id,itemUpdateDto);
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteItem(@PathVariable Long id){
        itemService.deleteItem(id);
        return null;
    }
    @PatchMapping("/ban_items/{sellerEmail}")
    public Object banItems(@PathVariable String sellerEmail){
        itemService.banItem(sellerEmail);
        return null;
    }
    @PatchMapping("/release_items/{sellerEmail}")
    public Object releaseItems(@PathVariable String sellerEmail){
        itemService.releaseBanItem(sellerEmail);
        return null;
    }
}
