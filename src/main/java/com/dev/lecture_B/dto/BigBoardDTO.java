package com.dev.lecture_B.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BigBoardDTO {
    private Long id;
    private String name;

    public BigBoardDTO(Long id,String name) {
        this.id = id;
        this.name = name;
    }
}
