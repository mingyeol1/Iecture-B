package com.dev.lecture_B.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMemberDTO {
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private boolean del;


}
