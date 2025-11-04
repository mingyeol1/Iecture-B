package com.dev.lecture_B.dto;

import com.dev.lecture_B.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyDTO {

    private Long id;
    private Long memberId;
    private Long boardId;
    private String content;

    public ReplyDTO(Long memberId) {
        this.memberId = memberId;
    }
}
