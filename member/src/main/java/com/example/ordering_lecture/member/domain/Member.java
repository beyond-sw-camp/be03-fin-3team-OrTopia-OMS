package com.example.ordering_lecture.member.domain;

import com.example.ordering_lecture.member.dto.MemberCreateReqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Embedded
    Address address;
//    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
//    private List<Ordering> orderings;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public static Member toMember(MemberCreateReqDto memberCreateReqDto){
        Address address = new Address(memberCreateReqDto.getCity()
                ,memberCreateReqDto.getStreet()
                ,memberCreateReqDto.getZipcode());
        return Member.builder()
                .name(memberCreateReqDto.getName())
                .email(memberCreateReqDto.getEmail())
                .password(memberCreateReqDto.getPassword())
                .address(address)
                .role(Role.USER)
                .build();
    }
}
