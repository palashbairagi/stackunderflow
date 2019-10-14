package com.cp.stackunderflow.service;

import com.cp.stackunderflow.entity.AnswerComment;
import com.cp.stackunderflow.exception.StackunderflowException;

public interface AnswerCommentService {
    public void addComment(AnswerComment comment, String token) throws StackunderflowException;
    public void updateComment(AnswerComment comment, String token) throws StackunderflowException;
}
