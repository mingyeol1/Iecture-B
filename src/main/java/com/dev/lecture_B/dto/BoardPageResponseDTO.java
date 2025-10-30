package com.dev.lecture_B.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardPageResponseDTO {
    private Long id;
    private String title;
    private String content;



}
