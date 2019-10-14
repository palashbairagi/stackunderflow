package com.cp.stackunderflow.repository;

import com.cp.stackunderflow.entity.Question;
import com.cp.stackunderflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    public List<Question> findByUser(User user);
}
