package com.dev.lecture_B.service;

import com.dev.lecture_B.dto.CreateMemberDTO;
import com.dev.lecture_B.dto.MemberResponseDTO;
import com.dev.lecture_B.entity.Member;
import com.dev.lecture_B.entity.RoleSet;
import com.dev.lecture_B.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


    public void createMember(CreateMemberDTO dto){

        //값을 넣기 위해 기존에 닉네임이 있는지 검증.
        if(memberRepository.existsByNickname(dto.getNickname())){
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
        };

        if (memberRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }

        Member member = new Member(dto.getNickname(), dto.getPassword(), dto.getEmail());
        member.addRole(RoleSet.USER);

        memberRepository.save(member);
    }

    public MemberResponseDTO findByMember(String email){
        Optional<Member> findByMember = memberRepository.findByEmail(email);

        if (findByMember.isPresent()){
            //Optional 자료를 통해 불러온 Member 가져오기
            Member member = findByMember.get();
            //DTO로 변환 후 값 출력
            return new MemberResponseDTO(member.getId(), member.getNickname(), member.getEmail());
        }else {
            //없을 시 null 출력
            throw  new NoSuchElementException("존재하지 않는 이메일");
        }

    }

    public void changeMember(Long id,String nickname, String email){
        //유저의 값이 존재하는지 먼저 확인
        Optional<Member> findMember = memberRepository.findById(id);

        Member member = findMember.orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 회원입니다.")
        );
        boolean existNickname = memberRepository.existsByNickname(nickname);
        boolean existEmail = memberRepository.existsByEmail(email);

        //데이터 베이스에 기존에 사용중인 이메일 및 닉네임이 있나 확인.
        //변경하려는 값이 현재 값과 다를때만 중복 검사.
        if (!member.getNickname().equals(nickname) && existNickname){ throw new IllegalArgumentException("이미 존재하는 닉네임입니다");}
        if (member.getEmail().equals(email) && existEmail){ throw new IllegalArgumentException("이미 존재하는 이메일입니다");}

        //save를 사용하지 않고 더티체킹으로 저장 될 수 있도록 만듦
        member.changeMember(nickname, email);

    }

    public List<MemberResponseDTO> findMemberAll(){
        List<Member> all = memberRepository.findAll();
        return all.stream().map(member ->
                new MemberResponseDTO(member.getId(),
                        member.getNickname(),
                        member.getEmail())).collect(Collectors.toList());
    }

    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }



}


