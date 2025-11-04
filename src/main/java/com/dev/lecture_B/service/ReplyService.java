package com.dev.lecture_B.service;

import com.dev.lecture_B.dto.ReplyDTO;
import com.dev.lecture_B.dto.ReplyResponseDTO;
import com.dev.lecture_B.entity.Board;
import com.dev.lecture_B.entity.Member;
import com.dev.lecture_B.entity.Reply;
import com.dev.lecture_B.repository.BoardRepository;
import com.dev.lecture_B.repository.MemberRepository;
import com.dev.lecture_B.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void register(ReplyDTO dto){

        Optional<Member> findMember = memberRepository.findById(dto.getMemberId());
        Optional<Board> findBoard = boardRepository.findById(dto.getBoardId());
        Member member = findMember.orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다."));
        Board board = findBoard.orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));

        //엔티티 객체를 저장하되 나중에 응답 객체로는 id 값만 불러올 수 있도록 만들 거임
        Reply reply = new Reply(dto.getContent(),member, board);

        replyRepository.save(reply);
    }

    public void change(ReplyDTO dto){

        Optional<Reply> findReply = replyRepository.findById(dto.getId());
        Reply reply = findReply.orElseThrow(() -> new NoSuchElementException("댓글이 존재하지 않습니다."));

        reply.changeReply(dto.getContent());
    }

    public Page<ReplyResponseDTO> list(Pageable pageable){
        Page<Reply> list = replyRepository.findAll(pageable);

        return list.map(reply -> new ReplyResponseDTO(reply.getId(),
                                                            reply.getMember().getId(),
                                                            reply.getMember().getNickname(),
                                                            reply.getContent()));
    }

    public void delete(Long id){
        replyRepository.deleteById(id);
    }
}
