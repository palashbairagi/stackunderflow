package com.cp.stackunderflow.service;


import com.cp.stackunderflow.entity.Question;
import com.cp.stackunderflow.exception.StackunderflowException;
import com.cp.stackunderflow.model.QuestionModel;

import java.util.List;

public interface QuestionService {
    public void addQuestion(Question question, String token) throws StackunderflowException;
    public void updateQuestion(Question question, String token) throws StackunderflowException;
    public List<QuestionModel> getQuestionsByUser(String token, Integer userId) throws StackunderflowException;
    public List<QuestionModel> getQuestions() throws StackunderflowException;
    public QuestionModel getQuestion(Integer questionId) throws StackunderflowException;
}
