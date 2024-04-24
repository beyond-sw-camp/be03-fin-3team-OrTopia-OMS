package com.example.ordering_lecture.orderdetail.entity;

import com.example.ordering_lecture.order.entity.Ordering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name="ordering_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Ordering ordering;
    @Column(nullable = false)
    private Long itemId;
    @Column(nullable = false)
    private Long sellerId;
    @Column(nullable = false)
    private int quantity;
    private int discountPrice;
    @Column
    private String options;
    @Column(nullable = false)
    @Builder.Default
    private boolean isReviewed = false;
    @CreationTimestamp
    private LocalDateTime createdTime;

    public void updateReview() {
        isReviewed = true;
    }
}
