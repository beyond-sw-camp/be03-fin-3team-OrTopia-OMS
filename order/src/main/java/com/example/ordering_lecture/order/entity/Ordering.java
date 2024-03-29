package com.example.ordering_lecture.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ordering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int totalPrice;
    @Column(nullable = false)
    private Long addressId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Statue statue = Statue.PAIED;
    @Column(nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod PaymentMethod;
    @Column(nullable = false)
    private String recipient;
    @CreationTimestamp
    private LocalDateTime createdTime;

    public void updateStatue(String statue){
        this.statue = Statue.valueOf(statue);
    }
}
