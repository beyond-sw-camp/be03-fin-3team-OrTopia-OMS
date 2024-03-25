package com.example.ordering_lecture.order.repository;

import com.example.ordering_lecture.order.domain.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Ordering,Long> {
    List<Ordering> findByMemberId(Long id);
}
