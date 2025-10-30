package com.dev.lecture_B.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private Long memberId;
    private Long bigBoardId;
    private String title;
    private String content;
    private String videoURL;
    private List<MultipartFile> imageURL;

    public BoardDTO(Long memberId, Long bigBoardId, String title, String content, String videoURL, List<MultipartFile> imageURL) {
        this.memberId = memberId;
        this.bigBoardId = bigBoardId;
        this.title = title;
        this.content = content;
        this.videoURL = videoURL;
        this.imageURL = imageURL;
    }

    public BoardDTO(String title, String content, String videoURL, List<MultipartFile> imageURL) {
        this.title = title;
        this.content = content;
        this.videoURL = videoURL;
        this.imageURL = imageURL;
    }
}
