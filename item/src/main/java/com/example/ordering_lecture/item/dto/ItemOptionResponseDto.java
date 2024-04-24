package com.example.ordering_lecture.item.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ItemOptionResponseDto {
    private String name;
    private List<String> value;
    public ItemOptionResponseDto(){
        value = new ArrayList<>();
    }
}
