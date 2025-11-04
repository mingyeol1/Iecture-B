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

    //파일이 저장될 URL
    @Value("${file.upload-dir}")
    private String imagesURL;

    public void registerBoard(BoardDTO dto) throws IOException {
        //dto로 데이터를 넘겨 받고 해당 유저 및 게시글이 있는지 확인
        Optional<Member> memberId = memberRepository.findById(dto.getMemberId());
        Optional<BigBoard> bigBoardId = bigBoardRepository.findById(dto.getBigBoardId());
        //없으면 에러 발생
        Member member = memberId.orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));
        BigBoard bigBoard = bigBoardId.orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시판입니다."));

        List<String> fileNames = new ArrayList<>();
//        List<MultipartFile> files = dto.getImageURL();
        //파일이 빈값이 있으면 에러가 나므로 Optional 처리
        List<MultipartFile> files = Optional.ofNullable(dto.getImageURL()).orElse(Collections.emptyList());
        Path uploadDir = Paths.get(imagesURL);
        Files.createDirectories(uploadDir);

        //파일의 기존이름과 UUID를 합쳐 경로에 저장.
        for (MultipartFile file : files){
            //기존 파일의 이름
            String originalName = file.getOriginalFilename();
            //UUID를 합친 파일의 이름
            String savedName = UUID.randomUUID() + "_" + originalName;
            //파일들이 저장될 경로.
            //해당 코드는 파일이 미리 만들어져 있지 않으면 에러가 나므로 주석처리. 나는 직접 파일을 만들었었음.
//            Path savePath = Paths.get(imagesURL, savedName);
            Path savePath = uploadDir.resolve(savedName);
            //file.getInputStream() < 읽어올 파일 데이터  savePath < 저장될 위치.
            Files.copy(file.getInputStream(), savePath);


            //List형태로 저장
            fileNames.add(savedName);

        }

        //위에서 받아온 데이터들을(dto) 저장
        Board board = new Board(member, bigBoard, dto.getTitle(), dto.getContent(), dto.getVideoURL(), fileNames);

        boardRepository.save(board);
    }

    public BoardResponseDTO changeBoard(BoardDTO dto) throws IOException {

        Board board = boardRepository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글입니다."));

        // 남길 이미지 목록 (클라이언트에서 보냄)
        List<String> remainImages = Optional.ofNullable(dto.getRemainImages()).orElse(new ArrayList<>());

        // 기존 이미지(DB에 있는) 중 삭제해야 할 파일 찾기
        List<String> deleteTargets = new ArrayList<>();
        for (String oldImage : board.getImageURL()) {
            if (!remainImages.contains(oldImage)) {
                deleteTargets.add(oldImage);
            }
        }

        // 실제 파일 삭제
        for (String fileName : deleteTargets) {
            Path path = Paths.get(imagesURL, fileName);
            Files.deleteIfExists(path);
        }

        // 현재 유지할 이미지 목록들 복사.
        List<String> updatedImages = new ArrayList<>(remainImages);

        // 기존 이미지가 비어있지 않으면 동작.
        if (dto.getImageURL() != null && !dto.getImageURL().isEmpty()) {
            //경로에 이미지폴더가 없으면 생성.
            Files.createDirectories(Paths.get(imagesURL));

            for (MultipartFile file : dto.getImageURL()) {
                // 파일이 빈값이면 넘어감
                if (file.isEmpty()) continue;

                String originalName = file.getOriginalFilename();
                String savedName = UUID.randomUUID() + "_" + originalName;

                Path savePath = Paths.get(imagesURL, savedName);
                Files.copy(file.getInputStream(), savePath);

                updatedImages.add(savedName);
            }
        }


        //기존에 있던 이미지를 다 삭제
        board.getImageURL().clear();
        //새로운 이미지로 다시 채워넣음
        //여기서 set말고 get을 사용하는 이유는 set은 단순히 기존 리스트 객체를 새로운 객체로 동작해서 JPA가 추적하지 않아 관리가 되지않음
        //get은 JPA가 관리하던 리스트 객체 내부의 데이터만 수정을 하는거라 더티체킹이 일어나게됨
        board.getImageURL().addAll(updatedImages);
        // 엔티티 내용 수정
        board.changeBoard(dto.getTitle(), dto.getContent(), dto.getVideoURL(), updatedImages);


        // 응답용 DTO 반환
        return new BoardResponseDTO(
                board.getId(),
                board.getMember().getId(),
                board.getBigBoard().getId(),
                board.getTitle(),
                board.getContent(),
                board.getVideoURL(),
                board.getImageURL()
        );
    }

    public BoardResponseDTO findBoardOne(Long id){
        Optional<Board> findBoard = boardRepository.findById(id);
        Board board = findBoard.orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글입니다."));

        //응답용 DTO를 만들어 객체 반환.
        return new BoardResponseDTO(
                board.getId(),
                board.getMember().getId(),
                board.getBigBoard().getId(),
                board.getTitle(),
                board.getContent(),
                board.getVideoURL(),
                board.getImageURL());
    }

    public Page<BoardPageResponseDTO> pageList(Pageable pageable){
        Page<Board> list = boardRepository.findAll(pageable);
       return list.map(board -> new BoardPageResponseDTO(board.getId(),
                                                                board.getTitle(),
                                                                board.getContent()));
    }

    public void deleteBoard(Long id) throws IOException{
        Optional<Board> findBoard = boardRepository.findById(id);
        Board board = findBoard.orElseThrow(() -> new NoSuchElementException("삭제할 게시글이 없습니다."));

        //경로에 파일이 있으면 그 파일도 삭제할 수 있도록 만듦
        for (String fileName : board.getImageURL()) {
            Path path = Paths.get(imagesURL, fileName);
            //경로에 파일이 있으면 삭제처리
            Files.deleteIfExists(path);
        }

        boardRepository.deleteById(id);
    }
}
