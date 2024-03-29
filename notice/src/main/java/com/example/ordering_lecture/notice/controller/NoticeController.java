package com.example.ordering_lecture.notice.controller;

import com.example.ordering_lecture.notice.dto.NoticeRequestDto;
import com.example.ordering_lecture.notice.dto.NoticeResponseDto;
import com.example.ordering_lecture.notice.dto.NoticeUpdateDto;
import com.example.ordering_lecture.notice.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice_server")
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/create")
    public String createNotice(@ModelAttribute NoticeRequestDto noticeRequestDto) {
        noticeService.createNotice(noticeRequestDto);
        return "ok";
    }
    @GetMapping("/notices")
    public List<NoticeResponseDto> notices() {
        return noticeService.showAllNotice();
    }
    @GetMapping("/notice/{id}")
    public Object findById(@PathVariable Long id){
        return noticeService.findById(id);
    }
    @PatchMapping("/update/{id}")
    public NoticeResponseDto updateNotice(@PathVariable Long id, @RequestBody NoticeUpdateDto noticeUpdateDto) {
        return noticeService.updateNotice(id, noticeUpdateDto);
    }

    @PatchMapping("/delete/{id}")
    public String deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return "ok";
    }
}