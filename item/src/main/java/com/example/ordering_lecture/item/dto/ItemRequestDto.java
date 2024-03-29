package com.example.ordering_lecture.item.dto;

import com.example.ordering_lecture.item.entity.Category;
import com.example.ordering_lecture.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    @NotNull
    private String name;
    private int stock;
    private int price;
    private String category;
    private String detail;
    private MultipartFile imagePath;
    private int minimumStock;
    private boolean delYN;
    private boolean isBaned;
    private String sellerEmail;

    public Item toEntity(){
        Category category = null;
        try {
            category = Category.valueOf(this.getCategory());
        }catch (Exception e){
            // TODO : Enum 값이 틀리거나, null이 들어온 경우의 에러를 잡는다.
            e.printStackTrace();
        }
        //TODO : S3 저장 후 나오는 url를 넣어줌
        String fileUrl = "testing";
        Item item = Item.builder()
                .name(this.getName())
                .price(this.getPrice())
                .category(category)
                .stock(this.getStock())
                .detail(this.getDetail())
                .minimumStock(this.getMinimumStock())
                .sellerEmail(this.getSellerEmail())
                .imagePath(fileUrl)
                .build();
        return item;
    }
}
