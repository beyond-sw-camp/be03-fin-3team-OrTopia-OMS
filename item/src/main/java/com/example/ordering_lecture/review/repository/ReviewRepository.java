package com.example.ordering_lecture.review.repository;

import com.example.ordering_lecture.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository  extends JpaRepository<Review,Long> {

    List<Review> findAllByItemId(Long itemId);

    List<Review> findAllByBuyerEmail(String email);
}
