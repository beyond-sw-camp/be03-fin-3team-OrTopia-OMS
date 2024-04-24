package com.example.ordering_lecture.notice.entity;

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
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Lob
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false)
    private String imagePath;
    @Column(nullable = false)
    @Builder.Default
    private boolean delYN=false;
    @Column(nullable = false)
    private String startDate;
    @Column(nullable = false)
    private String endDate;
    public void updateName(String name){
        this.name = name;
    }
    public void updateContents(String contents){
        this.contents = contents;
    }
    public void updateStartDate(String startDate){
        this.startDate = startDate;
    }
    public void updateEndDate(String endDate){
        this.endDate = endDate;
    }
    public void deleteNotice(){
        this.delYN = true;
    }
}
