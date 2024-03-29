package com.example.ordering_lecture.item.service;

import com.example.ordering_lecture.item.dto.ItemRequestDto;
import com.example.ordering_lecture.item.dto.ItemResponseDto;
import com.example.ordering_lecture.item.dto.ItemUpdateDto;
import com.example.ordering_lecture.item.entity.Item;
import com.example.ordering_lecture.item.repository.ItemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemResponseDto createItem(ItemRequestDto itemRequestDto) {
        Item item = itemRequestDto.toEntity();
        itemRepository.save(item);
        return null;
    }

    public List<ItemResponseDto> showAllItem(){
        return itemRepository.findAll().stream()
                .map(ItemResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemResponseDto updateItem(Long id, ItemUpdateDto itemUpdateDto) {
        //TODO : 에러 코드 추후 수정
        Item item = itemRepository.findById(id).orElseThrow();
        item = itemUpdateDto.toUpdate(item);
        return ItemResponseDto.toDto(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
//        Item item = itemRepository.findById(id).orElseThrow();
//        item.deleteItem();
    }

    public List<ItemResponseDto> banItem(String sellerEmail){
        List<Item> items = itemRepository.findAllBySellerEmail(sellerEmail);
        for(Item item : items){
            item.banItem();
        }
        return items.stream()
                .map(ItemResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public List<ItemResponseDto>  releaseBanItem(String sellerEmail){
        List<Item> items = itemRepository.findAllBySellerEmail(sellerEmail);
        for(Item item : items){
            item.releaseBanItem();
        }
        return items.stream()
                .map(ItemResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public List<ItemResponseDto> findItemByEmail(String sellerEmail) {
        List<Item> items = itemRepository.findAllBySellerEmail(sellerEmail);
        return items.stream()
                .map(ItemResponseDto::toDto)
                .collect(Collectors.toList());
    }
}
