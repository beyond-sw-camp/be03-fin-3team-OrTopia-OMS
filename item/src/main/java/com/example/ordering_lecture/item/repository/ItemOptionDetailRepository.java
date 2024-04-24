package com.example.ordering_lecture.item.repository;

import com.example.ordering_lecture.item.entity.ItemOptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOptionDetailRepository extends JpaRepository<ItemOptionDetail,Long> {
    List<ItemOptionDetail> findAllByItemOptionId(Long id);
}
