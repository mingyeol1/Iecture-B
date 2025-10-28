package com.dev.lecture_B;

import com.dev.lecture_B.entity.*;
import com.dev.lecture_B.repository.BigBoardRepository;
import com.dev.lecture_B.repository.BoardRepository;
import com.dev.lecture_B.repository.MemberRepository;
import com.dev.lecture_B.repository.ReplyRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dev.lecture_B.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class CrudTest {
    @Autowired
    BigBoardRepository bigBoardRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReplyRepository replyRepository;

    @PersistenceContext
    EntityManager em;


    JPAQueryFactory queryFactory;

    @Test
    public void TestCRUD(){
        queryFactory = new JPAQueryFactory(em);
        Member member = new Member("TestMember", "1234", "Test@email.com");
        member.addRole(RoleSet.USER);
        memberRepository.save(member);

        BigBoard bigBoard = new BigBoard("테스트 게시판");
        bigBoardRepository.save(bigBoard);

        Board board = new Board(member,bigBoard,"테스트 게시글", "테스트 글 내용", "URL1", "URL2");
        boardRepository.save(board);

        Reply reply = new Reply("댓글 내용", member,board);
        replyRepository.save(reply);

        //생성된 회원정보 확인
        Member resultMember = queryFactory
                .selectFrom(QMember.member)
                .fetchOne();

        BigBoard resultBigBoard = queryFactory
                .selectFrom(QBigBoard.bigBoard)
                .fetchOne();

        Board resultBoard = queryFactory
                .selectFrom(QBoard.board)
                .fetchOne();

        Reply resultReply = queryFactory
                .selectFrom(QReply.reply)
                .fetchOne();

        assertThat(resultMember).isEqualTo(member);
        assertThat(resultBigBoard).isEqualTo(bigBoard);
        assertThat(resultBoard).isEqualTo(board);
        assertThat(resultReply).isEqualTo(reply);

        //현재 같은 영속성 컨텍스트에 있어서 save를 굳이 하지 않아도 수정이 반영됨
        //변경된 회원정보 및 게시글 수정
        member.changeMember("Member","Member@Email.com");
        board.changeBoard("ㅋㅋㅋㅋ","아아 수정",null,null);
        reply.changeReply("답글 수정입니다.");


        Member chengeMember = queryFactory
                .selectFrom(QMember.member)
                .fetchOne();
        Board changeBoard = queryFactory
                .selectFrom(QBoard.board)
                .fetchOne();
        Reply changeReply = queryFactory
                .selectFrom(QReply.reply)
                .fetchOne();

        assertThat(chengeMember.getNickName()).isEqualTo("Member");
        assertThat(changeBoard.getTitle()).isEqualTo("ㅋㅋㅋㅋ");
        assertThat(changeBoard.getImageURL()).isEqualTo("URL2");
        assertThat(changeBoard.getVideoURL()).isEqualTo("URL1");
        assertThat(changeReply.getContent()).isEqualTo("답글 수정입니다.");



    }
}
