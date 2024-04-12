package com.example.ordering_lecture.address.service;

import com.example.ordering_lecture.address.domain.Address;
import com.example.ordering_lecture.address.dto.AddressRequestDto;
import com.example.ordering_lecture.address.dto.AddressResponseDto;
import com.example.ordering_lecture.address.dto.AddressUpdateDto;
import com.example.ordering_lecture.address.repository.AddressRepository;
import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    public AddressService(AddressRepository addressRepository, MemberRepository memberRepository) {
        this.addressRepository = addressRepository;
        this.memberRepository = memberRepository;
    }
    public Address createAddress(String email, AddressRequestDto addressRequestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new OrTopiaException(ErrorCode.NOT_FOUND_MEMBER));
        Address address = addressRequestDto.toEntity(member);
        return addressRepository.save(address);
    }

    public AddressResponseDto findById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new OrTopiaException(ErrorCode.NOT_FOUND_ADDRESS));
        return AddressResponseDto.toDto(address);
    }
    public List<AddressResponseDto> showAllAddress(){
        return addressRepository.findAll().stream()
                .map(AddressResponseDto::toDto)
                .collect(Collectors.toList());
    }
    public List<AddressResponseDto> findAllAddressesByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new OrTopiaException(ErrorCode.NOT_FOUND_MEMBER));
        List<Address> addresses = addressRepository.findAllByMember(member);
        if (addresses.isEmpty()) {
            throw new OrTopiaException(ErrorCode.NOT_FOUND_ADDRESS_BY_EMAIL);
        }
        return addresses.stream()
                .map(AddressResponseDto::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public AddressResponseDto updateAddress(Long id, AddressUpdateDto addressUpdateDto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new OrTopiaException(ErrorCode.NOT_FOUND_ADDRESS));
        address.updateAddress(addressUpdateDto);
        return AddressResponseDto.toDto(address);
    }

    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new OrTopiaException(ErrorCode.NOT_FOUND_ADDRESS);
        }
        addressRepository.deleteById(id);
    }
}