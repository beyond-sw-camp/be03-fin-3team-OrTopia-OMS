package com.example.ordering_lecture.notice.dto;

import com.example.ordering_lecture.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeUpdateDto {
    private String name;
    private String contents;
    private String startDate;
    private String endDate;

    public Notice toUpdate(Notice notice) {
        if (name != null) {
            notice.updateName(name);
        }
        if (contents != null) {
            notice.updateContents(contents);
        }
        if (startDate != null) {
            notice.updateStartDate(startDate);
        }
        if (endDate != null) {
            notice.updateEndDate(endDate);
        }
        return notice;
    }
}
