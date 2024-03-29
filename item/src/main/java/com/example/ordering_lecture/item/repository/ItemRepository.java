package com.example.ordering_lecture.item.repository;

import com.example.ordering_lecture.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findAllBySellerEmail(String sellerEmail);
}
