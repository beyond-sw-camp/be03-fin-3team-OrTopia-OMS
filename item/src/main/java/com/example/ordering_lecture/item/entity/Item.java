package com.example.ordering_lecture.item.entity;

import com.example.ordering_lecture.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private int price;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
    @Column(nullable = false)
    private String detail;
    @Column(nullable = false)
    private String imagePath;
    @Column
    private int minimumStock;
    @Column(nullable = false)
    @Builder.Default
    private boolean delYN=false;
    @Column(nullable = false)
    @Builder.Default
    private boolean isBaned=false;
    @Column(nullable = false)
    private Long sellerId;
    @OneToMany(mappedBy = "item",fetch = FetchType.LAZY, orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Review> review;
    @CreationTimestamp
    private LocalDateTime createdTime;

    public void updateName(String name){
        this.name = name;
    }
    public void updateStock(int stock){
        this.stock = stock;
    }
    public void updatePrice(int price){
        this.price = price;
    }
    public void updateCategory(Category category){
        this.category = category;
    }
    public void updateDetail(String detail){
        this.detail = detail;
    }
    public void updateImagePath(String imagePath){
        this.imagePath = imagePath;
    }
    public void updateMinimumStock(int minimumStock){
        this.minimumStock = minimumStock;
    }
    public void updateSellerId(Long sellerId){
        this.sellerId = sellerId;
    }
    public void banItem(){
        this.isBaned = true;
    }
    public void releaseBanItem(){
        this.isBaned = false;
    }
    public void deleteItem(){
        this.delYN = true;
    }
}
