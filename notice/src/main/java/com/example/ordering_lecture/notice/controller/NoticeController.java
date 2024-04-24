package com.example.ordering_lecture.notice.controller;

import com.example.ordering_lecture.common.OrTopiaResponse;
import com.example.ordering_lecture.notice.dto.NoticeRequestDto;
import com.example.ordering_lecture.notice.dto.NoticeResponseDto;
import com.example.ordering_lecture.notice.dto.NoticeUpdateDto;
import com.example.ordering_lecture.notice.service.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrTopiaResponse> createNotice(@ModelAttribute NoticeRequestDto noticeRequestDto) {
        NoticeResponseDto noticeResponseDto = noticeService.createNotice(noticeRequestDto);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("create success",noticeResponseDto);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.CREATED);
    }

    @GetMapping("/notices")
    public ResponseEntity<OrTopiaResponse> getAllNotices() {
        List<NoticeResponseDto> noticeResponseDtos = noticeService.showAllNotice();
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success",noticeResponseDtos);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }

    @GetMapping("/notice/{id}")
    public ResponseEntity<OrTopiaResponse> getNoticeById(@PathVariable Long id) {
        NoticeResponseDto noticeResponseDto = noticeService.findById(id);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success", noticeResponseDto);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<OrTopiaResponse> updateNotice(@PathVariable Long id, @RequestBody NoticeUpdateDto noticeUpdateDto) {
        NoticeResponseDto updatedNotice = noticeService.updateNotice(id, noticeUpdateDto);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("update success", updatedNotice);
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<OrTopiaResponse> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("delete success",null);
        return new ResponseEntity<>(orTopiaResponse,HttpStatus.OK);
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        return noticeService.uploadFileToS3(file);
    }
}