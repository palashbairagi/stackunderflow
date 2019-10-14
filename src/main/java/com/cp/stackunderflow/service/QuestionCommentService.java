package com.cp.stackunderflow.service;

import com.cp.stackunderflow.entity.AnswerComment;
import com.cp.stackunderflow.entity.QuestionComment;
import com.cp.stackunderflow.exception.StackunderflowException;

public interface QuestionCommentService {
    public void addComment(QuestionComment comment, String token) throws StackunderflowException;
    public void updateComment(QuestionComment comment, String token) throws StackunderflowException;
}
