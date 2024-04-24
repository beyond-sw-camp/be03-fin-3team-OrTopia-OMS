package com.example.ordering_lecture.member.repository;

import com.example.ordering_lecture.member.domain.LikedSeller;
import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedSellerRepository extends JpaRepository<LikedSeller, Long> {
    List<LikedSeller> findAllByBuyer(Member buyer);
    List<LikedSeller> findByBuyerAndSeller(Member buyer, Seller seller);
    List<LikedSeller> findBySeller(Seller seller);
}