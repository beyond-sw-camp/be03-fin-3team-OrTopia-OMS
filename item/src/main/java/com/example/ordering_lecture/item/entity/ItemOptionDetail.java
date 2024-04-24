package com.example.ordering_lecture.item.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemOptionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String value;
    @JoinColumn(name="item_option_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemOption itemOption;
}
