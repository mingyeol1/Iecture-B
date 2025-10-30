package com.dev.lecture_B.service;

import com.dev.lecture_B.dto.BoardDTO;
import com.dev.lecture_B.dto.BoardPageResponseDTO;
import com.dev.lecture_B.dto.BoardResponseDTO;
import com.dev.lecture_B.entity.BigBoard;
import com.dev.lecture_B.entity.Board;
import com.dev.lecture_B.entity.Member;
import com.dev.lecture_B.repository.BigBoardRepository;
import com.dev.lecture_B.repository.BoardRepository;
import com.dev.lecture_B.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BigBoardRepository bigBoardRepository;

    @Value("${file.upload-dir}")
    private String imagesURL;

    public void saveBoard(BoardDTO dto) throws IOException {
        //dto로 데이터를 넘겨 받고 해당 유저 및 게시글이 있는지 확인
        Optional<Member> memberId = memberRepository.findById(dto.getMemberId());
        Optional<BigBoard> bigBoardId = bigBoardRepository.findById(dto.getBigBoardId());
        //없으면 에러 발생
        Member member = memberId.orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));
        BigBoard bigBoard = bigBoardId.orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시판입니다."));

        List<String> fileNames = new ArrayList<>();
        List<MultipartFile> files = dto.getImageURL();

        //파일의 기존이름과 UUID를 합쳐 경로에 저장.
        for (MultipartFile file : files){
            //기존 파일의 이름
            String originalName = file.getOriginalFilename();
            //UUID를 합친 파일의 이름
            String savedName = UUID.randomUUID() + "_" + originalName;
            //파일들이 저장될 경로.
            Path savePath = Paths.get(imagesURL, savedName);
            //file.getInputStream() < 읽어올 파일 데이터  savePath < 저장될 위치.
            Files.copy(file.getInputStream(), savePath);

            //List형태로 저장
            fileNames.add(savedName);

        }

        //위에서 받아온 데이터들을(dto) 저장
        Board board = new Board(member, bigBoard, dto.getTitle(), dto.getContent(), dto.getVideoURL(), fileNames);

        boardRepository.save(board);
    }

//    public void changeBoard(BoardDTO dto) throws IOException{
//        Optional<Board> findBoard = boardRepository.findById(dto.getId());
//
//        Board board = findBoard.orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글 입니다."));
//
//        board.changeBoard(dto.getTitle(), dto.getContent(), dto.getVideoURL(), dto.getImageURL());
//
//
//    }

    public BoardResponseDTO findBoardOne(Long id){
        Optional<Board> findBoard = boardRepository.findById(id);
        Board board = findBoard.orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글입니다."));

        //빌더패턴으로 바꾸면 더 깔끔해 보일듯.
        return new BoardResponseDTO(board.getId(),board.getMember().getId(), board.getBigBoard().getId(), board.getTitle(), board.getContent(), board.getVideoURL(), board.getImageURL());
    }

    public Page<BoardPageResponseDTO> pageList(Pageable pageable){
        Page<Board> list = boardRepository.findAll(pageable);
       return list.map(board -> new BoardPageResponseDTO(board.getId(), board.getTitle(), board.getContent()));
    }

    public void deleteBoard(Long id) throws IOException{
        Optional<Board> findBoard = boardRepository.findById(id);
        Board board = findBoard.orElseThrow(() -> new NoSuchElementException("삭제할 게시글이 없습니다."));

        //경로에 파일이 있으면 그 파일도 삭제할 수 있도록 만듦
        for (String fileName : board.getImageURL()) {
            Path path = Paths.get(imagesURL, fileName);
            Files.deleteIfExists(path);
        }

        boardRepository.deleteById(id);
    }
}
