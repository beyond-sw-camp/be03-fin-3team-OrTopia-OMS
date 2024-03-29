package com.example.ordering_lecture.member.repository;

import com.example.ordering_lecture.member.domain.LikedSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedSellerRepository extends JpaRepository<LikedSeller, Long> {
    List<LikedSeller> findAllByBuyerId(Long id);
}
