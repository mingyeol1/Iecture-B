package com.dev.lecture_B.controller;

import com.dev.lecture_B.dto.BigBoardDTO;
import com.dev.lecture_B.service.BigBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bigboard")
public class BigBoardController {
    private final BigBoardService bigBoardService;

    @PostMapping("/create")
    public ResponseEntity<?> createBigBoard(@RequestBody BigBoardDTO dto){

        bigBoardService.save(dto);


        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/list")
    public ResponseEntity<?> findByAll(){
        List<BigBoardDTO> findAll = bigBoardService.findByAll();
        return ResponseEntity.ok().body(findAll);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBigBoard(@PathVariable Long id){
        bigBoardService.deleteBigBoard(id);
        return ResponseEntity.ok().body(200);
    }
}
