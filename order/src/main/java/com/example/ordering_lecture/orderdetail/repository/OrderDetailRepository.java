package com.example.ordering_lecture.orderdetail.repository;

import com.example.ordering_lecture.orderdetail.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findAllByOrderingId(Long orderId);
}
