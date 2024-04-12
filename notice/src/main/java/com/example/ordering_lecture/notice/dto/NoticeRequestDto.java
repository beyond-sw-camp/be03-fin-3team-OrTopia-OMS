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
    private MultipartFile imagePath;
    private String startDate;
    private String endDate;

    public Notice toEntity(String fileUrl){
        Notice notice = Notice.builder()
                .name(this.getName())
                .imagePath(fileUrl)
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .build();
        return notice;
    }
}