package com.dev.lecture_B.repository;

import com.dev.lecture_B.entity.BigBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BigBoardRepository extends JpaRepository<BigBoard, Long> {
}
