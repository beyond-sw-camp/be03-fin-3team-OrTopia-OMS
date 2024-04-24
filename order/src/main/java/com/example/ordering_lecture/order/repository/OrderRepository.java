package com.example.ordering_lecture.order.repository;

import com.example.ordering_lecture.order.dto.BuyerGraphCountData;
import com.example.ordering_lecture.order.dto.BuyerGraphPriceData;
import com.example.ordering_lecture.order.entity.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Ordering,Long> {
    List<Ordering> findAllByEmail(String email);
    @Query("SELECT new com.example.ordering_lecture.order.dto.BuyerGraphPriceData(DATE(o.createdTime) as createdTime, SUM(o.totalPrice) as price) FROM Ordering o WHERE o.createdTime BETWEEN :startDate AND :endDate AND o.statue = 'COMPLETE_DELIVERY' AND o.email = :email GROUP BY DATE(o.createdTime)")
    List<BuyerGraphPriceData> findSumPriceByDateBetweenAndStatueAndEmail(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("email") String email);

    @Query("SELECT new com.example.ordering_lecture.order.dto.BuyerGraphCountData(DATE(o.createdTime) as createdTime, COUNT(*) as count) FROM Ordering o WHERE o.createdTime BETWEEN :startDate AND :endDate AND o.statue = 'COMPLETE_DELIVERY' AND o.email = :email GROUP BY DATE(o.createdTime)")
    List<BuyerGraphCountData> findCompletedOrdersByEmailAndDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("email") String email);
}
