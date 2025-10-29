package com.dev.lecture_B.controller;

import com.dev.lecture_B.dto.CreateMemberDTO;
import com.dev.lecture_B.dto.MemberRequestDTO;
import com.dev.lecture_B.dto.MemberResponseDTO;
import com.dev.lecture_B.entity.Member;
import com.dev.lecture_B.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody CreateMemberDTO dto){
        memberService.createMember(dto);

        return ResponseEntity.status(200).build();
    }

    //추후 스프링 시큐리티 설정 후 바꿀 예정
    @GetMapping("/mypage")
    public ResponseEntity<?> myPage(@RequestBody MemberResponseDTO dto){ //파라미터 값을 없애고 로그인 한 정보를 불러오도록 바꿀 예정

        MemberResponseDTO findMember = memberService.findByMember(dto.getEmail());

        return ResponseEntity.ok().body(findMember);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id
                                            ,@RequestBody MemberRequestDTO dto){
        memberService.changeMember(dto.getId(), dto.getNickname() , dto.getEmail());

        return ResponseEntity.ok(200);
    }


    @GetMapping("/list")
    public ResponseEntity<?> findByMemberAll(){
        List<MemberResponseDTO> memberAll = memberService.findMemberAll();

        return ResponseEntity.ok().body(memberAll);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);

        return ResponseEntity.ok(204);
    }
}
