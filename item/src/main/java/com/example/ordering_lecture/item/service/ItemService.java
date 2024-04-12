package com.example.ordering_lecture.item.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import com.example.ordering_lecture.item.controller.MemberServiceClient;
import com.example.ordering_lecture.item.dto.ItemRequestDto;
import com.example.ordering_lecture.item.dto.ItemResponseDto;
import com.example.ordering_lecture.item.dto.ItemUpdateDto;
import com.example.ordering_lecture.item.entity.Item;
import com.example.ordering_lecture.item.repository.ItemRepository;
import com.example.ordering_lecture.redis.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final RedisService redisService;
    private final AmazonS3Client amazonS3Client;
    private final MemberServiceClient memberServiceClient;
    public ItemService(ItemRepository itemRepository, RedisService redisService, AmazonS3Client amazonS3Client, MemberServiceClient memberServiceClient) {
        this.itemRepository = itemRepository;
        this.redisService = redisService;
        this.amazonS3Client = amazonS3Client;
        this.memberServiceClient = memberServiceClient;
    }
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public ItemResponseDto createItem(ItemRequestDto itemRequestDto, String email) throws OrTopiaException {
        String fileName = itemRequestDto.getName() + System.currentTimeMillis();
        String fileUrl = null;
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(itemRequestDto.getImagePath().getContentType());
            metadata.setContentLength(itemRequestDto.getImagePath().getSize());
            amazonS3Client.putObject(bucket, fileName, itemRequestDto.getImagePath().getInputStream(), metadata);
            fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();

            Long sellerId = memberServiceClient.searchIdByEmail(email);
            Item item = itemRequestDto.toEntity(fileUrl, sellerId);
            itemRepository.save(item);
            return ItemResponseDto.toDto(item);
        } catch (Exception e) {
            throw new OrTopiaException(ErrorCode.S3_SERVER_ERROR);
        }
    }


    public List<ItemResponseDto> showAllItem(){
        return itemRepository.findAll().stream()
                .map(ItemResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemResponseDto updateItem(Long id, ItemUpdateDto itemUpdateDto) {
        Item item = itemRepository.findById(id).orElseThrow(()->new OrTopiaException(ErrorCode.NOT_FOUND_ITEM));
        if(!itemUpdateDto.getImagePath().isEmpty()){
            String fileUrl = item.getImagePath();
            String splitStr = ".com/";
            amazonS3Client.deleteObject(
                    new DeleteObjectRequest(bucket,fileUrl.substring(fileUrl.lastIndexOf(splitStr)+splitStr.length())));
            String fileName = itemUpdateDto.getName()+System.currentTimeMillis();
            fileUrl = null;
            try {
                ObjectMetadata metadata= new ObjectMetadata();
                metadata.setContentType(itemUpdateDto.getImagePath().getContentType());
                metadata.setContentLength(itemUpdateDto.getImagePath().getSize());
                amazonS3Client.putObject(bucket,fileName,itemUpdateDto.getImagePath().getInputStream(),metadata);
                fileUrl = amazonS3Client.getUrl(bucket,fileName).toString();
            } catch (Exception e) {
                throw new OrTopiaException(ErrorCode.S3_SERVER_ERROR);
            }
            item = itemUpdateDto.toUpdate(item,fileUrl);
        }else{
            item = itemUpdateDto.toUpdate(item,item.getImagePath());
        }
        return ItemResponseDto.toDto(item);
    }

    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                ()-> new OrTopiaException(ErrorCode.NOT_FOUND_ITEM)
                );
        itemRepository.deleteById(id);
        String fileUrl = item.getImagePath();
        String splitStr = ".com/";
        amazonS3Client.deleteObject(
                new DeleteObjectRequest(bucket,fileUrl.substring(fileUrl.lastIndexOf(splitStr)+splitStr.length())));
//        Item item = itemRepository.findById(id).orElseThrow();
//        item.deleteItem();
    }

    public List<ItemResponseDto> banItem(Long sellerId)throws OrTopiaException{
        List<Item> items = itemRepository.findAllBySellerId(sellerId);
        if(items.isEmpty()){
            throw new OrTopiaException(ErrorCode.EMPTY_ITEMS);
        }
        for(Item item : items){
            item.banItem();
        }
        return items.stream()
                .map(ItemResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public List<ItemResponseDto>  releaseBanItem(Long sellerId)throws OrTopiaException{
        List<Item> items = itemRepository.findAllBySellerId(sellerId);
        if(items.isEmpty()){
            throw new OrTopiaException(ErrorCode.EMPTY_ITEMS);
        }
        for(Item item : items){
            item.releaseBanItem();
        }
        return items.stream()
                .map(ItemResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public List<ItemResponseDto> findItemByEmail(Long sellerId)throws OrTopiaException {
        List<Item> items = itemRepository.findAllBySellerId(sellerId);
        if(items.isEmpty()){
            throw new OrTopiaException(ErrorCode.EMPTY_ITEMS);
        }
        return items.stream()
                .map(ItemResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public ItemResponseDto readItem(Long id, String email) {
        Item item = itemRepository.findById(id).orElseThrow(
                ()->new OrTopiaException(ErrorCode.NOT_FOUND_ITEM)
                );
        ItemResponseDto itemResponseDto = ItemResponseDto.toDto(item);
        redisService.setValues(email,itemResponseDto);
        return itemResponseDto;
    }

    public List<ItemResponseDto> readRecentItems(String email) {
        Set<String> set = redisService.getValues(email);
        List<ItemResponseDto> itemResponseDtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(String str: set){
            ItemResponseDto  itemResponseDto = null;
            try {
                itemResponseDto = objectMapper.readValue(str, ItemResponseDto.class);
            } catch (JsonProcessingException e) {
                throw new OrTopiaException(ErrorCode.JSON_PARSE_ERROR);
            }
            itemResponseDtos.add(itemResponseDto);
        }
        return itemResponseDtos;
    }

    public String getImagePath(Long itemId) {
        Item item = itemRepository.findImagePathById(itemId).orElseThrow(()->new OrTopiaException(ErrorCode.NOT_FOUND_ITEM));
        return item.getImagePath();
    }
}
