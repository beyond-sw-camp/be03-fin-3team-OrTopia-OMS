package com.example.ordering_lecture.address.controller;

import com.example.ordering_lecture.address.dto.AddressRequestDto;
import com.example.ordering_lecture.address.dto.AddressResponseDto;
import com.example.ordering_lecture.address.dto.AddressUpdateDto;
import com.example.ordering_lecture.address.service.AddressService;
import com.example.ordering_lecture.common.OrTopiaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrTopiaResponse> createAddress(@RequestHeader("myEmail") String email, @RequestBody AddressRequestDto addressRequestDto) {
        addressService.createAddress(email, addressRequestDto);
        OrTopiaResponse response = new OrTopiaResponse("Address created successfully", null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity<OrTopiaResponse> addresses() {
        List<AddressResponseDto> addressList = addressService.showAllAddress();
        OrTopiaResponse response = new OrTopiaResponse("Addresses fetched successfully", addressList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrTopiaResponse> findAddress(@PathVariable Long id) {
        AddressResponseDto address = addressService.findById(id);
        OrTopiaResponse response = new OrTopiaResponse("Address fetched successfully", address);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/by-email")
    public ResponseEntity<OrTopiaResponse> allAddressesByEmail(@RequestHeader("myEmail") String email) {
        List<AddressResponseDto> addresses = addressService.findAllAddressesByEmail(email);
        OrTopiaResponse response = new OrTopiaResponse("Addresses by email fetched successfully", addresses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<OrTopiaResponse> updateAddress(@PathVariable Long id, @RequestBody AddressUpdateDto addressUpdateDto) {
        AddressResponseDto updatedAddress = addressService.updateAddress(id, addressUpdateDto);
        OrTopiaResponse response = new OrTopiaResponse("Address updated successfully", updatedAddress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<OrTopiaResponse> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        OrTopiaResponse response = new OrTopiaResponse("Address deleted successfully", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}