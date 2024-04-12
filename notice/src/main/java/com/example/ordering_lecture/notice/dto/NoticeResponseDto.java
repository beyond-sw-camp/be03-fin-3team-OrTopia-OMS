package com.example.ordering_lecture.notice.dto;

import com.example.ordering_lecture.notice.entity.Notice;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class NoticeResponseDto {
    private Long id;
    private String name;
    private String imagePath;
    private String startDate;
    private String endDate;

    public static NoticeResponseDto toDto(Notice notice){
        return NoticeResponseDto.builder()
                .id(notice.getId())
                .name(notice.getName())
                .imagePath(notice.getImagePath())
                .startDate(notice.getStartDate())
                .endDate(notice.getEndDate())
                .build();
    }
}
