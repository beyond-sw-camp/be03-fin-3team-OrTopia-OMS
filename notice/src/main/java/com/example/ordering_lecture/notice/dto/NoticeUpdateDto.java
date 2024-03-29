package com.example.ordering_lecture.notice.dto;

import com.example.ordering_lecture.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeUpdateDto {
    private String name;
    private MultipartFile imagePath;
    private String startDate;
    private String endDate;

    public Notice toUpdate(Notice notice){
        if(name !=null){
            notice.updateName(name);
        }
        if(imagePath !=null){
            String url = "updateTest";
            notice.updateImagePath(url);
        }
        if(startDate !=null){
            notice.updateStartDate(startDate);
        }
        if(endDate !=null){
            notice.updateEndDate(endDate);
        }
        return notice;
    }
}
