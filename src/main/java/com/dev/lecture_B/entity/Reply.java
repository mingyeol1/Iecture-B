package com.dev.lecture_B.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

}
