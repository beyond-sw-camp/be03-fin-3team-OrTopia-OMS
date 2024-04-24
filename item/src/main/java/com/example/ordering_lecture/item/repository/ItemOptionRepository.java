package com.example.ordering_lecture.item.repository;

import com.example.ordering_lecture.item.entity.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOptionRepository  extends JpaRepository<ItemOption,Long> {
    List<ItemOption> findAllByItemId(Long id);

}
