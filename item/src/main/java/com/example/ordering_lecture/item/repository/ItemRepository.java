package com.example.ordering_lecture.item.repository;

import com.example.ordering_lecture.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findAllBySellerId(Long sellerId);
    Optional<Item> findImagePathById(Long itemId);
}