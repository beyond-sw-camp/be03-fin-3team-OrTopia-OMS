package com.example.ordering_lecture.notice.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.ordering_lecture.notice.dto.NoticeRequestDto;
import com.example.ordering_lecture.notice.dto.NoticeResponseDto;
import com.example.ordering_lecture.notice.dto.NoticeUpdateDto;
import com.example.ordering_lecture.notice.entity.Notice;
import com.example.ordering_lecture.notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.InputStream;
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

    public NoticeResponseDto createNotice(NoticeRequestDto noticeRequestDto) {
        String fileName = noticeRequestDto.getName() + System.currentTimeMillis();
        String fileUrl = null;
        try (InputStream inputStream = noticeRequestDto.getImagePath().getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(noticeRequestDto.getImagePath().getContentType());
            metadata.setContentLength(noticeRequestDto.getImagePath().getSize());
            amazonS3Client.putObject(bucket, fileName, inputStream, metadata);
            fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (Exception e) {
            throw new RuntimeException("S3에 이미지 업로드 실패", e);
        }
        Notice notice = noticeRequestDto.toEntity(fileUrl);
        notice.updateImagePath(fileUrl);
        Notice savedNotice = noticeRepository.save(notice);
        return NoticeResponseDto.toDto(savedNotice);
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
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));

        if (!noticeUpdateDto.getImagePath().isEmpty()) {
            // 이미지 처리 로직
            String fileUrl = notice.getImagePath();
            if (fileUrl != null && !fileUrl.isEmpty()) {
                String splitStr = ".com/";
                amazonS3Client.deleteObject(
                        new DeleteObjectRequest(bucket, fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length())));
            }
            String fileName = noticeUpdateDto.getName() + System.currentTimeMillis();
            fileUrl = null;
            try (InputStream inputStream = noticeUpdateDto.getImagePath().getInputStream()) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(noticeUpdateDto.getImagePath().getContentType());
                metadata.setContentLength(noticeUpdateDto.getImagePath().getSize());

                amazonS3Client.putObject(bucket, fileName, inputStream, metadata);
                fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();
            } catch (Exception e) {
                throw new RuntimeException("S3 서버 오류");
            }
            notice = noticeUpdateDto.toUpdate(notice, fileUrl);
        } else {
            notice = noticeUpdateDto.toUpdate(notice); // 여기가 수정된 부분입니다.
        }
        return NoticeResponseDto.toDto(notice);
    }
    @Transactional
    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow();
        notice.deleteNotice();
    }
}