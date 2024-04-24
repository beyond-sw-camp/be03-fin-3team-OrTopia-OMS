package com.example.ordering_lecture.item.dto;

import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
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
    @NotNull(message =  "EMPTY_ITEM_NAME")
    private String name;
    @NotNull(message =  "EMPTY_ITEM_STOCK")
    private int stock;
    @NotNull(message =  "EMPTY_ITEM_PRICE")
    private int price;
    @NotNull(message =  "EMPTY_ITEM_CATEGORY")
    private String category;
    @NotNull(message =  "EMPTY_ITEM_DETAIL")
    private String detail;
    private MultipartFile imagePath;
    private int minimumStock;
    private boolean delYN;
    private boolean isBaned;
//    @NotNull(message =  "EMPTY_ITEM_SELLER")
    private Long sellerId;

    public Item toEntity(String fileUrl, Long SellerId) throws OrTopiaException {
        //TODO : S3 저장 후 나오는 url를 넣어줌
        try {
            Category category = null;
            category = Category.valueOf(this.getCategory());
            return Item.builder()
                    .reviewNumber(0L)
                    .score(0L)
                    .name(this.getName())
                    .price(this.getPrice())
                    .category(category)
                    .stock(this.getStock())
                    .detail(this.getDetail())
                    .minimumStock(this.getMinimumStock())
                    .sellerId(SellerId)
                    .imagePath(fileUrl)
                    .build();
        }catch (Exception e){
            throw new OrTopiaException(ErrorCode.WRONG_ITEM_INFORMATION);
        }
    }
}
