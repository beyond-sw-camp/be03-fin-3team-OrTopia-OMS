package com.example.ordering_lecture.item.dto;

import com.example.ordering_lecture.item.entity.Category;
import com.example.ordering_lecture.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemUpdateDto {
    private String name;
    private Integer stock;
    private Integer price;
    private String category;
    private String detail;
    private MultipartFile imagePath;
    private Integer minimumStock;
    private boolean delYN;
    private boolean isBaned;
    private Long sellerId;

    public Item toUpdate(Item item,String url){
        if(name !=null){
            item.updateName(name);
        }
        if(stock != null){
            item.updateStock(stock);
        }
        if(price != null){
            item.updatePrice(price);
        }
        if(category !=null){
            item.updateCategory(Category.valueOf(category));
        }
        if(detail !=null){
            item.updateDetail(detail);
        }
        if(imagePath !=null){
            item.updateImagePath(url);
        }
        if(minimumStock !=null){
            item.updateMinimumStock(minimumStock);
        }
        if(sellerId !=null){
            item.updateSellerId(sellerId);
        }
        return item;
    }
}
