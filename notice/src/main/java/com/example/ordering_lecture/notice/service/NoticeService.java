package com.example.ordering_lecture.notice.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import com.example.ordering_lecture.notice.dto.NoticeRequestDto;
import com.example.ordering_lecture.notice.dto.NoticeResponseDto;
import com.example.ordering_lecture.notice.dto.NoticeUpdateDto;
import com.example.ordering_lecture.notice.entity.Notice;
import com.example.ordering_lecture.notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public NoticeService(NoticeRepository noticeRepository, AmazonS3Client amazonS3Client) {
        this.noticeRepository = noticeRepository;
        this.amazonS3Client = amazonS3Client;
    }
    public String uploadFileToS3(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        try {
            // 파일 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            // S3에 파일 업로드
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata));

            // 업로드된 파일의 URL 반환
            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("s3에 업로드 실패", e);
        }
    }
    public NoticeResponseDto createNotice(NoticeRequestDto noticeRequestDto) throws OrTopiaException {
        if (noticeRequestDto.getName() == null || noticeRequestDto.getName().isEmpty()) {
            throw new OrTopiaException(ErrorCode.EMPTY_NOTICE_TITLE);
        }
        if(noticeRequestDto.getImagePath() != null){
            String fileName = System.currentTimeMillis() + "_" + noticeRequestDto.getImagePath().getOriginalFilename();
            try {
                // 파일 메타데이터 설정
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(noticeRequestDto.getImagePath() .getContentType());
                metadata.setContentLength(noticeRequestDto.getImagePath() .getSize());

                // S3에 파일 업로드
                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, noticeRequestDto.getImagePath() .getInputStream(), metadata));

                // 업로드된 파일의 URL 반환
                String fileUrl =  amazonS3Client.getUrl(bucket, fileName).toString();
                Notice notice = noticeRequestDto.toEntity(fileUrl);
                return NoticeResponseDto.toDto(noticeRepository.save(notice));
            } catch (IOException e) {
                throw new RuntimeException("s3에 업로드 실패", e);
            }
        }
        Notice notice = noticeRequestDto.toEntity(null);
        Notice savedNotice = noticeRepository.save(notice);
        return NoticeResponseDto.toDto(savedNotice);
    }
    public List<NoticeResponseDto> showAllNotice() throws OrTopiaException {
        List<Notice> notices = noticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new OrTopiaException(ErrorCode.EMPTY_NOTICE_CONTENTS);
        }
        return notices.stream().filter(notice -> !notice.isDelYN()).map(NoticeResponseDto::toDto).collect(Collectors.toList());
    }
    public NoticeResponseDto findById(Long id) throws OrTopiaException {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new OrTopiaException(ErrorCode.NOT_FOUND_NOTICE));
        return NoticeResponseDto.toDto(notice);
    }

    @Transactional
    public NoticeResponseDto updateNotice(Long id, NoticeUpdateDto noticeUpdateDto) throws OrTopiaException {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new OrTopiaException(ErrorCode.NOT_FOUND_NOTICE));
        notice = noticeUpdateDto.toUpdate(notice);
        noticeRepository.save(notice);
        return NoticeResponseDto.toDto(notice);
    }

    @Transactional
    public void deleteNotice(Long id) throws OrTopiaException {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new OrTopiaException(ErrorCode.DELETED_NOTICE));
        notice.deleteNotice();
    }
}