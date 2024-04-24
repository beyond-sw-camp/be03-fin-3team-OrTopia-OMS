package com.example.ordering_lecture.email;

import com.example.ordering_lecture.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "member_id")
    @Getter @Setter
    private Member member;

    @Getter @Setter
    private Date expiryDate;

    public void setExpiryDate(int minutes) {
        Instant now = Instant.now();
        this.expiryDate = Date.from(now.plusSeconds(minutes * 60));
    }

}
