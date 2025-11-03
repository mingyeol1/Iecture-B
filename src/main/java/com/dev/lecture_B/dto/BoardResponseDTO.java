package com.dev.lecture_B.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BoardResponseDTO {
    private Long id;
    private Long memberId;
    private Long bigBoardId;
    private String title;
    private String content;
    private String videoURL;
    private List<String> imageURL = new ArrayList<>();

    public BoardResponseDTO(Long id, Long memberId, Long bigBoardId, String title, String content, String videoURL, List<String> imageURL) {
        this.id = id;
        this.memberId = memberId;
        this.bigBoardId = bigBoardId;
        this.title = title;
        this.content = content;
        this.videoURL = videoURL;
        this.imageURL = imageURL;
    }


}
