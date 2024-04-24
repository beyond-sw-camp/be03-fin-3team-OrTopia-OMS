package com.example.ordering_lecture.item.dto;

import com.example.ordering_lecture.item.entity.Item;
import com.example.ordering_lecture.item.entity.ItemOptionQuantity;
import lombok.Data;

@Data
public class ItemOptionQuantityDto {
    private String value1;
    private String value2;
    private String value3;

    public ItemOptionQuantityDto(){
        value1 = "NONE";
        value2 = "NONE";
        value3 = "NONE";
    }
    public void setValue(int index,String value){
        if(index==0){
            value1 = value;
        }else if(index ==1){
            value2 = value;
        }else{
            value3 = value;
        }
    }

    public ItemOptionQuantity toEntity(Item item) {
        return ItemOptionQuantity.builder()
                .quantity(item.getStock())
                .item(item)
                .value1(value1)
                .value2(value2)
                .value3(value3)
                .build();
    }
}
