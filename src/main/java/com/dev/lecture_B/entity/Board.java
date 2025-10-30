package com.dev.lecture_B.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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
    private String videoURL;
    private List<String> imageURL = new ArrayList<>();

    public Board(Member member, BigBoard bigBoard, String title, String content, String videoURL, List<String> imageURL) {
        this.member = member;
        this.bigBoard = bigBoard;
        this.title = title;
        this.content = content;
        this.videoURL = videoURL;
        this.imageURL = imageURL;
    }

    //게시글을 수정하는 메서드
    public void changeBoard(String title, String content,String videoURL,List<String> imageURL){
        if (title != null){
            this.title = title;
        }
        if (content != null){
            this.content = content;
        }
        if (videoURL != null){
            this.videoURL = videoURL;
        }
        if (imageURL != null){
            this.imageURL = imageURL;
        }
    }
}
