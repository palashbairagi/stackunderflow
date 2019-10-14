package com.cp.stackunderflow.repository;

import com.cp.stackunderflow.entity.AnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment, Integer> {
}
