package com.dev.lecture_B.repository;

import com.dev.lecture_B.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
