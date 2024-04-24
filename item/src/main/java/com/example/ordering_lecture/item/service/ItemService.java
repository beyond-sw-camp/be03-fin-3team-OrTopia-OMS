package com.example.ordering_lecture.item.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import com.example.ordering_lecture.item.controller.MemberServiceClient;
import com.example.ordering_lecture.item.dto.*;
import com.example.ordering_lecture.item.entity.Item;
import com.example.ordering_lecture.item.entity.ItemOption;
import com.example.ordering_lecture.item.entity.ItemOptionDetail;
import com.example.ordering_lecture.item.entity.ItemOptionQuantity;
import com.example.ordering_lecture.item.repository.ItemOptionDetailRepository;
import com.example.ordering_lecture.item.repository.ItemOptionQuantityRepository;
import com.example.ordering_lecture.item.repository.ItemOptionRepository;
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
    private final ItemOptionDetailRepository itemOptionDetailRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final ItemOptionQuantityRepository itemOptionQuantityRepository;
    public ItemService(ItemRepository itemRepository, RedisService redisService, AmazonS3Client amazonS3Client, MemberServiceClient memberServiceClient, ItemOptionDetailRepository itemOptionDetailRepository, ItemOptionRepository itemOptionRepository, ItemOptionQuantityRepository itemOptionQuantityRepository) {
        this.itemRepository = itemRepository;
        this.redisService = redisService;
        this.amazonS3Client = amazonS3Client;
        this.memberServiceClient = memberServiceClient;
        this.itemOptionDetailRepository = itemOptionDetailRepository;
        this.itemOptionRepository = itemOptionRepository;
        this.itemOptionQuantityRepository = itemOptionQuantityRepository;
    }
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public ItemResponseDto createItem(ItemRequestDto itemRequestDto,List<OptionRequestDto> optionRequestDtos ,String email) throws OrTopiaException {
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
            // 옵션이 존재하는 경우
            if(!optionRequestDtos.isEmpty()){
                List<List<String>> allOptionValue = new ArrayList<>();
                int index = 0;
                for(OptionRequestDto optionRequestDto :optionRequestDtos){
                    allOptionValue.add(new ArrayList<>());
                    ItemOption itemOption = ItemOption.builder()
                            .name(optionRequestDto.getOptionName())
                            .item(item)
                            .build();
                    itemOptionRepository.save(itemOption);
                    for(String value:optionRequestDto.getDetails()){
                        ItemOptionDetail itemOptionDetail = ItemOptionDetail.builder()
                                .itemOption(itemOption)
                                // 옵션의 이름
                                .value(value)
                                .build();
                        allOptionValue.get(index).add(value);
                        itemOptionDetailRepository.save(itemOptionDetail);
                    }
                    index++;
                }
                fillAllOption(item,allOptionValue,new ArrayList<>(),allOptionValue.size(),0);
            }else{
                ItemOptionQuantityDto itemOptionQuantityDto = new ItemOptionQuantityDto();
                ItemOptionQuantity itemOptionQuantity = itemOptionQuantityDto.toEntity(item);
                itemOptionQuantityRepository.save(itemOptionQuantity);
                //재고를 레디스에 저장
                redisService.setItemQuantity(itemOptionQuantity.getId(),item.getStock());
            }
            return ItemResponseDto.toDto(item);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrTopiaException(ErrorCode.S3_SERVER_ERROR);
        }
    }

    // 받아온 옵션의 모든 경우의 수를 계산하여 DB에 저장.
    public void fillAllOption(Item item, List<List<String>> allOptionValue,List<String> nowOption,int maxDepth,int nowDepth){
        if(maxDepth == nowDepth){
            ItemOptionQuantityDto itemOptionQuantityDto = new ItemOptionQuantityDto();
            for(int i=0;i<nowDepth;i++){
                itemOptionQuantityDto.setValue(i,nowOption.get(i));
            }
            ItemOptionQuantity itemOptionQuantity = itemOptionQuantityDto.toEntity(item);
            itemOptionQuantityRepository.save(itemOptionQuantity);
            redisService.setItemQuantity(itemOptionQuantity.getId(),item.getStock());
            return;
        }
        for(String option : allOptionValue.get(nowDepth)){
            nowOption.add(option);
            fillAllOption(item,allOptionValue,nowOption,maxDepth,nowDepth+1);
            nowOption.remove(nowOption.size()-1);
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

    public List<ItemResponseDto> findItemByEmail(String email)throws OrTopiaException {
        Long sellerId = memberServiceClient.searchIdByEmail(email);
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
        // 최근본 상품을 저장하기 위함
        redisService.setValues(email,itemResponseDto);
        // 옵션을 불러옴
        List<ItemOption> itemOptions = itemOptionRepository.findAllByItemId(id);
        if(!itemOptions.isEmpty()){
            for(ItemOption itemOption : itemOptions){
                ItemOptionResponseDto itemOptionResponseDto = new ItemOptionResponseDto();
                itemOptionResponseDto.setName(itemOption.getName());
                List<ItemOptionDetail> itemOptionDetails = itemOptionDetailRepository.findAllByItemOptionId(itemOption.getId());
                for(ItemOptionDetail itemOptionDetail : itemOptionDetails){
                    itemOptionResponseDto.getValue().add(itemOptionDetail.getValue());
                }
                itemResponseDto.getItemOptionResponseDtoList().add(itemOptionResponseDto);
            }
        }
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

    public List<RecommendationRedisData> findRecommendItem(String email) {
        Long id = memberServiceClient.searchIdByEmail(email);
        List<String> list = redisService.getValues2(id);
        List<RecommendationRedisData> recommendationRedisDatas = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(String str: list){
            RecommendationRedisData  recommendationRedisData = null;
            try {
                recommendationRedisData = objectMapper.readValue(str, RecommendationRedisData.class);
            } catch (JsonProcessingException e) {
                throw new OrTopiaException(ErrorCode.JSON_PARSE_ERROR);
            }
            recommendationRedisDatas.add(recommendationRedisData);
        }
        return recommendationRedisDatas;
    }

    public ItemResponseDto readItemForMyPage(Long itemId, String email) {
        Item item = itemRepository.findImagePathById(itemId).orElseThrow(() -> new OrTopiaException(ErrorCode.NOT_FOUND_ITEM));
        ItemResponseDto itemResponseDto = ItemResponseDto.toDto(item);
        return itemResponseDto;
    }

    public Long searchIdByOptionDetail(Long itemId, List<String> values) {
        String value1 = "NONE";
        String value2 = "NONE";
        String value3 = "NONE";
        if(values.size()==1){
            value1 = values.get(0);
        }
        if(values.size()==2){
            value1 = values.get(0);
            value2 = values.get(1);
        }
        if(values.size()==3){
            value1 = values.get(0);
            value2 = values.get(1);
            value3 = values.get(2);
        }
        ItemOptionQuantity itemOptionQuantity = itemOptionQuantityRepository.findItemOptionQuantity(itemId,value1,value2,value3).orElseThrow(
                () -> new OrTopiaException(ErrorCode.NOT_FOUND_OPTION)
        );
        return itemOptionQuantity.getId();
    }
}
