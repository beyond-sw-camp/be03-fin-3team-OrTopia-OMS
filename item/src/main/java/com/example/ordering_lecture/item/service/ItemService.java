package com.example.ordering_lecture.item.service;

import com.example.ordering_lecture.item.domain.Item;
import com.example.ordering_lecture.item.dto.ItemReqDto;
import com.example.ordering_lecture.item.dto.ItemResDto;
import com.example.ordering_lecture.item.dto.ItemSearchDto;
import com.example.ordering_lecture.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemService {
    @Autowired
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item create(ItemReqDto itemReqDto) {
        MultipartFile multipartFile = itemReqDto.getItemImage();
        String fileName = multipartFile.getOriginalFilename();
        Item item = Item.builder()
                .name(itemReqDto.getName())
                .category(itemReqDto.getCategory())
                .price(itemReqDto.getPrice())
                .stockQuantity(itemReqDto.getStockQuantity())
                .build();
        Item savedItem = itemRepository.save(item);
        Path path = Paths.get("C:/Users/imjy2/Desktop/tmp/",savedItem.getId()+"_"+fileName);
        savedItem.setImagePath(path.toString());
        try {
            byte[] bytes = multipartFile.getBytes();
            Files.write(path,bytes, StandardOpenOption.CREATE,StandardOpenOption.WRITE);
        } catch (IOException e) {
            // TODO : Unchecked exception 으로 바꿔줘서 transactional rollback 처리 가능하게
            throw new IllegalArgumentException("image not available");
        }
        return item;
    }

    public Item delete(Long id){
        Item item = itemRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("not find item"));
        item.deleteItem();
        return item;
    }

    public Resource getImage(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("not found item"));
        Path path = Paths.get(item.getImagePath());
        Resource resource = null;
        try{
            resource = new UrlResource(path.toUri());
        }catch (MalformedURLException e) {
            throw new IllegalArgumentException("image url form is not valid");
        }
        return resource;
    }

    public Item update(Long id,ItemReqDto itemReqDto) {
        Item item = itemRepository.findById(id).orElseThrow(()->new EntityNotFoundException("not found item"));
        MultipartFile multipartFile = itemReqDto.getItemImage();
        String fileName = multipartFile.getOriginalFilename();
        Path path = Paths.get("C:/Users/imjy2/Desktop/tmp/",item.getId()+"_"+fileName);
        item.setImagePath(path.toString());
        item.updateItem(itemReqDto.getName()
                ,itemReqDto.getCategory()
                ,itemReqDto.getPrice()
                ,itemReqDto.getStockQuantity()
                ,path.toString());
        try {
            byte[] bytes = multipartFile.getBytes();
            Files.write(path,bytes, StandardOpenOption.CREATE,StandardOpenOption.WRITE);
        } catch (IOException e) {
            // TODO : Unchecked exception 으로 바꿔줘서 transactional rollback 처리 가능하게
            throw new IllegalArgumentException("image not available");
        }
        return item;
    }

    public List<ItemResDto> findAll(ItemSearchDto itemSearchDto, Pageable pageable) {
        // 검색을 위해 Specification 객체 사용
        // Specification객체는 복잡한 쿼리를 명세를 이용해 정의하여 쉽게 생성
        Specification<Item> spec = new Specification<Item>() {
            @Override
            // root : Entity의 속성을 접근하기 위한 객체, CriteriaBuilder : 쿼리를 생성하기 위한 객체
            public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(itemSearchDto.getName() != null){
                    predicates.add(criteriaBuilder.like(root.get("name"),"%"+ itemSearchDto.getName()+"%"));
                }
                if(itemSearchDto.getCategory() != null){
                    predicates.add(criteriaBuilder.like(root.get("category"),"%"+ itemSearchDto.getCategory()+"%"));
                }
                predicates.add(criteriaBuilder.equal(root.get("delYn"),"N"));
                Predicate[] predicateArr = new Predicate[predicates.size()];
                for(int i=0;i<predicates.size();i++){
                    predicateArr[i] = predicates.get(i);
                }
                Predicate predicate = criteriaBuilder.and(predicateArr);
                return predicate;
            }
        };
        Page<Item> items = itemRepository.findAll(spec,pageable);
        List<Item> itemList = items.getContent();
        List<ItemResDto> itemResDtos = new ArrayList<>();
        itemResDtos = itemList.stream().map(a-> ItemResDto.builder()
                .name(a.getName())
                .id(a.getId())
                .stockQuantity(a.getStockQuantity())
                .price(a.getPrice())
                .imagePath(a.getImagePath())
                .category(a.getCategory())
                .build())
                .collect(Collectors.toList());
        return itemResDtos;
    }
}
