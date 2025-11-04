package com.dev.lecture_B.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyResponseDTO {
    private Long id;

    private Long boardId;
    //댓글 작성자
    private String nickname;
    //댓글 내용
    private String content;


    public ReplyResponseDTO(Long id, Long boardId, String nickname, String content) {
        this.id = id;
        this.boardId = boardId;
        this.nickname = nickname;
        this.content = content;
    }
}
