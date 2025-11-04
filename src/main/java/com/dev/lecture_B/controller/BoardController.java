package com.dev.lecture_B.controller;

import com.dev.lecture_B.dto.BoardDTO;
import com.dev.lecture_B.dto.BoardPageResponseDTO;
import com.dev.lecture_B.dto.BoardResponseDTO;
import com.dev.lecture_B.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@ModelAttribute BoardDTO dto){

        try {
            boardService.registerBoard(dto);
            return ResponseEntity.ok(200);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBoardOne(@PathVariable Long id){
        BoardResponseDTO findBoard = boardService.findBoardOne(id);

        return ResponseEntity.ok().body(findBoard);
    }

    @GetMapping("/list")
    public ResponseEntity<?> boardList(Pageable pageable){

        Page<BoardPageResponseDTO> dto = boardService.pageList(pageable);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@ModelAttribute BoardDTO dto){
        try {
            BoardResponseDTO boardResponseDTO = boardService.changeBoard(dto);
            return ResponseEntity.ok(boardResponseDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id){
        try {
            boardService.deleteBoard(id);
            return ResponseEntity.ok(204);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
