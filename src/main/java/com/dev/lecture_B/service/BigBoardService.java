package com.dev.lecture_B.service;

import com.dev.lecture_B.dto.BigBoardDTO;
import com.dev.lecture_B.entity.BigBoard;
import com.dev.lecture_B.repository.BigBoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
public class BigBoardService {
    private final BigBoardRepository bigBoardRepository;

    public BigBoardDTO register(BigBoardDTO bigBoardDTO){
        BigBoard bigBoard = new BigBoard(bigBoardDTO.getName());
        bigBoardRepository.save(bigBoard);

        return bigBoardDTO;
    }

    public List<BigBoardDTO> findByAll(){
        List<BigBoard> all = bigBoardRepository.findAll();
        return all.stream().map(bigBoard -> new BigBoardDTO(bigBoard.getId(), bigBoard.getName()))
                .collect(Collectors.toList());
    }

    public void deleteBigBoard(Long id){
        bigBoardRepository.deleteById(id);
    }

}
