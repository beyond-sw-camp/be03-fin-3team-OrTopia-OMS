package com.example.ordering_lecture.address.repository;

import com.example.ordering_lecture.address.domain.Address;
import com.example.ordering_lecture.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByMember(Member member);
}