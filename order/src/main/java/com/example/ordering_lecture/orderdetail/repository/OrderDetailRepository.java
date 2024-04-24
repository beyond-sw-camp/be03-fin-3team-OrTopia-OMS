package com.example.ordering_lecture.orderdetail.repository;

import com.example.ordering_lecture.order.dto.SellerGraphCountData;
import com.example.ordering_lecture.order.dto.SellerGraphPriceData;
import com.example.ordering_lecture.orderdetail.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findAllByOrderingId(Long orderId);

    @Query("SELECT new com.example.ordering_lecture.order.dto.SellerGraphPriceData(DATE(od.createdTime) as createdTime, SUM(od.discountPrice) as price) FROM OrderDetail od JOIN od.ordering o WHERE o.statue = 'COMPLETE_DELIVERY' AND od.sellerId = :sellerId AND od.createdTime BETWEEN :startDate AND :endDate GROUP BY DATE(od.createdTime)")
    List<SellerGraphPriceData> findSalesData(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("sellerId") Long sellerId);

    @Query("SELECT new com.example.ordering_lecture.order.dto.SellerGraphCountData(DATE(od.createdTime) as createdTime, COUNT(*) as count) FROM OrderDetail od JOIN od.ordering o WHERE o.statue = 'COMPLETE_DELIVERY' AND od.sellerId = :sellerId AND od.createdTime BETWEEN :startDate AND :endDate GROUP BY DATE(od.createdTime)")
    List<SellerGraphCountData> findSalesDataBySellerIdAndDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("sellerId") Long sellerId);
}
