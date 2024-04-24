package com.example.ordering_lecture.notice.dto;

import com.example.ordering_lecture.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequestDto {
    @NotNull
    private String name;
    @NotNull
    private String contents;
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    private MultipartFile imagePath;

    public Notice toEntity(String imagePath){
        Notice notice = Notice.builder()
                .name(this.getName())
                .contents(this.getContents())
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .imagePath(imagePath)
                .build();
        return notice;
    }
}