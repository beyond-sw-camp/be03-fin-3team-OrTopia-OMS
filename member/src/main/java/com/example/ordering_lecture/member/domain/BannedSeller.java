package com.example.ordering_lecture.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannedSeller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Seller seller;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    @Builder.Default
    private boolean delYN = false;

    public void banCancel() {
        this.delYN = true;
    }
}
