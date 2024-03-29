package com.example.ordering_lecture.notice.repository;

import com.example.ordering_lecture.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
