package com.example.ordering_lecture.member.domain;

import com.example.ordering_lecture.address.domain.Address;
import com.example.ordering_lecture.member.dto.Buyer.MemberNewpasswordRequestDto;
import com.example.ordering_lecture.member.dto.Buyer.MemberUpdateDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Address> addresses;

    @Column(nullable = false)
    private byte age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.BUYER;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @Builder.Default
    private boolean delYN = false;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column
    private String lastLoginTime;

    @OneToOne(mappedBy = "member", cascade = CascadeType.PERSIST)
    private Seller seller;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.PERSIST)
    private List<LikedSeller> likedSellers;

    public void updateMember(MemberUpdateDto memberUpdateDto){
        if(memberUpdateDto.getEmail() != null){
            this.email = memberUpdateDto.getEmail();
        }
        if(memberUpdateDto.getName() != null){
            this.name = memberUpdateDto.getName();
        }
        if(memberUpdateDto.getPassword() != null){
            this.password = memberUpdateDto.getPassword();
        }
        if(memberUpdateDto.getAge() != 0){
            this.age = memberUpdateDto.getAge();
        }
        if(memberUpdateDto.getGender() != null){
            this.gender = memberUpdateDto.getGender();
        }
        if(memberUpdateDto.getPhoneNumber() != null){
            this.phoneNumber = memberUpdateDto.getPhoneNumber();
        }
    }
    public void updateRoleToSeller(){
        this.role = Role.SELLER;
    }

    public void deleteMember(){
        this.delYN = true;
    }



    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

}