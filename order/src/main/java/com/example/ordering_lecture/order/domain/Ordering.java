package com.example.ordering_lecture.order.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Ordering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false) //디폴트 컬럼이름 member_id
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ORDERED;

    @OneToMany(mappedBy = "ordering",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItemList = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @Builder
    public Ordering(Long memberId){
        this.memberId =memberId;
    }

    public void cancelOder(){
        this.orderStatus = OrderStatus.CANCELED;
    }
}
