package com.example.ordering_lecture.notice.dto;

import com.example.ordering_lecture.notice.entity.Notice;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeResponseDto {
    private Long id;
    private String name;
    private String contents;
    private String startDate;
    private String endDate;
    private boolean delYn;
    private String imagePath;

    public static NoticeResponseDto toDto(Notice notice){
        return NoticeResponseDto.builder()
                .id(notice.getId())
                .name(notice.getName())
                .contents(notice.getContents())
                .startDate(notice.getStartDate())
                .endDate(notice.getEndDate())
                .delYn(notice.isDelYN())
                .imagePath(notice.getImagePath())
                .build();
    }
}
