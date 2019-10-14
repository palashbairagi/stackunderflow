package com.cp.stackunderflow.repository;

import com.cp.stackunderflow.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
