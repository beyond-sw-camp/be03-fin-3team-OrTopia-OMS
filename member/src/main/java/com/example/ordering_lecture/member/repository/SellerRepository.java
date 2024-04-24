package com.example.ordering_lecture.member.repository;

import com.example.ordering_lecture.member.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findByDelYNFalse();

    Optional<Seller> findByMemberId(Long sellerId);
}
