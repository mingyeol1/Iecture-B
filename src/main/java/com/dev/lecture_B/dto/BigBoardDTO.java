package com.dev.lecture_B.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BigBoardDTO {
    private String name;

    public BigBoardDTO(String name) {
        this.name = name;
    }
}
