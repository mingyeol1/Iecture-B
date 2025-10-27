package com.dev.lecture_B.repository;

import com.dev.lecture_B.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
