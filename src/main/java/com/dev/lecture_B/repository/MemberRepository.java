package com.dev.lecture_B.repository;

import com.dev.lecture_B.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
