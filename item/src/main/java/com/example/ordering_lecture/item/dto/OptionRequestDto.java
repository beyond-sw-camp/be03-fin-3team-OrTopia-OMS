package com.example.ordering_lecture.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionRequestDto {
    private String optionName;
    private List<String> details;
}
