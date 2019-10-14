package com.cp.stackunderflow.service;

import com.cp.stackunderflow.entity.Answer;
import com.cp.stackunderflow.exception.StackunderflowException;

public interface AnswerService {
    public void addAnswer(Answer answer, String token) throws StackunderflowException;
    public void updateAnswer(Answer answer, String token) throws StackunderflowException;
}
