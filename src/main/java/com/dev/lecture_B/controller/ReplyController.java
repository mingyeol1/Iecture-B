package com.dev.lecture_B.controller;

import com.dev.lecture_B.dto.ReplyDTO;
import com.dev.lecture_B.dto.ReplyResponseDTO;
import com.dev.lecture_B.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ReplyDTO dto){
        replyService.register(dto);

        return ResponseEntity.ok(200);
    }
    @PutMapping("/update")
    public ResponseEntity<?> change(@RequestBody ReplyDTO dto){
        replyService.change(dto);

        return ResponseEntity.ok(200);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ReplyResponseDTO>> list(Pageable pageable){
        Page<ReplyResponseDTO> list = replyService.list(pageable);

        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        replyService.delete(id);

        return ResponseEntity.ok(204);
    }
}
