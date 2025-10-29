package com.dev.lecture_B.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDTO {
    private Long id;
    private String nickname;
    private String email;
    private String password;
}
