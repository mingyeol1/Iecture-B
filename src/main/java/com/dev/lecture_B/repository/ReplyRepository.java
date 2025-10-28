package com.dev.lecture_B.repository;

import com.dev.lecture_B.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
