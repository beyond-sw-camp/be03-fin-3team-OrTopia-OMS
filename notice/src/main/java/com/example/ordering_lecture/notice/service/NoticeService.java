package com.example.ordering_lecture.notice.service;

import com.example.ordering_lecture.notice.dto.NoticeRequestDto;
import com.example.ordering_lecture.notice.dto.NoticeResponseDto;
import com.example.ordering_lecture.notice.dto.NoticeUpdateDto;
import com.example.ordering_lecture.notice.entity.Notice;
import com.example.ordering_lecture.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }
    public Notice createNotice(NoticeRequestDto noticeRequestDto){
        Notice notice = noticeRequestDto.toEntity();
        return noticeRepository.save(notice);
    }
    public List<NoticeResponseDto> showAllNotice(){
        return noticeRepository.findAll().stream()
                .map(NoticeResponseDto::toDto)
                .collect(Collectors.toList());
    }
    public NoticeResponseDto findById(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow();
        return NoticeResponseDto.toDto(notice);
    }
    @Transactional
    public NoticeResponseDto updateNotice(Long id, NoticeUpdateDto noticeUpdateDto) {
        Notice notice = noticeRepository.findById(id).orElseThrow();
        notice = noticeUpdateDto.toUpdate(notice);
        return NoticeResponseDto.toDto(notice);
    }
    @Transactional
    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow();
        notice.deleteNotice();
    }
}