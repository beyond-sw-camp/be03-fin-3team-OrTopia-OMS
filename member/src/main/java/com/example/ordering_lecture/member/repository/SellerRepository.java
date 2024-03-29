package com.example.ordering_lecture.member.repository;

import com.example.ordering_lecture.member.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findByDelYNFalse();
}
