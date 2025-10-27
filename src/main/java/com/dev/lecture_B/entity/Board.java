package com.dev.lecture_B.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bigBoard_id")
    private BigBoard bigBoard;
    private String title;
    private String content;
    private String writer;
    private String videoURL;
    private String imageURL;
}
