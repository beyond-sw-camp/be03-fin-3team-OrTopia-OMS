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
public class ItemOptionQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String value1;
    @Column
    private String value2;
    @Column
    private String value3;
    @Column
    private int quantity;
    @JoinColumn(name="item_id",nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;
}
