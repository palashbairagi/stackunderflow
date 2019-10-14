package com.cp.stackunderflow.repository;

import com.cp.stackunderflow.entity.QuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionCommentRepository extends JpaRepository <QuestionComment, Integer> {
}
