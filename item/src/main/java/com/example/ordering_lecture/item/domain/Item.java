package com.example.ordering_lecture.item.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private int price;
    private int stockQuantity;
    private String imagePath;
    // TODO : boolean vs String ?
    // TODO : Column default로 처리하는 방법에 대해서.
    @Builder.Default
    private String delYn="N";
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public void deleteItem(){
        this.delYn = "Y";
    }
    // TODO : update라는 메서드 네이밍에 좀더 부합한 코드
    public void updateStockQuantity(int newQuantity){
        this.stockQuantity -= newQuantity;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }
    public void updateItem(String name,String category,int price, int stockQuantity,String imagePath){
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity =stockQuantity;
        this.imagePath = imagePath;
    }
}
