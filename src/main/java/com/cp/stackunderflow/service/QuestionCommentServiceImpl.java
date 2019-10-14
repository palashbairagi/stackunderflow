package com.cp.stackunderflow.service;

import com.cp.stackunderflow.entity.QuestionComment;
import com.cp.stackunderflow.entity.User;
import com.cp.stackunderflow.exception.StackunderflowException;
import com.cp.stackunderflow.repository.QuestionCommentRepository;
import com.cp.stackunderflow.security.TokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionCommentServiceImpl implements QuestionCommentService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionCommentServiceImpl.class);

    @Autowired
    QuestionCommentRepository questionCommentRepository;

    @Autowired
    TokenHandler tokenHandler;

    @Override
    public void addComment(QuestionComment comment, String token) throws StackunderflowException {
        try {
            int id = tokenHandler.getIdFromToken(token);

            if (id == 0 || !tokenHandler.validate(token, id))
                throw new StackunderflowException(1001, "Unauthorized user");

            User user = new User();
            user.setId(id);

            comment.setUser(user);
            questionCommentRepository.save(comment);

        } catch (StackOverflowError se) {
            logger.error("QuestionCommentServiceImpl[addComment(QuestionComment, String)] : " + se);
            throw se;
        } catch (Exception e) {
            logger.error("QuestionCommentServiceImpl[addComment(QuestionComment, String)] : " + e);
            throw new StackunderflowException(1051, "Unable to add comment");
        }
    }

    @Override
    public void updateComment(QuestionComment comment, String token) throws StackunderflowException {
        try {
            int id = tokenHandler.getIdFromToken(token);

            Optional<QuestionComment> questionComment = questionCommentRepository.findById(comment.getId());
            if (questionComment == null)
                throw new StackunderflowException(1053, "Comment not found");

            if (id == 0 || !tokenHandler.validate(token, id) || questionComment.get().getUser().getId() != id)
                throw new StackunderflowException(1001, "Unauthorized user");

            User user = new User();
            user.setId(id);

            comment.setUser(user);
            questionCommentRepository.save(comment);

        } catch (StackOverflowError se) {
            logger.error("QuestionCommentServiceImpl[updateComment(QuestionComment, String)] : " + se);
            throw se;
        } catch (Exception e) {
            logger.error("QuestionCommentServiceImpl[updateComment(QuestionComment, String)] : " + e);
            throw new StackunderflowException(1052, "Unable to update comment");
        }
    }
}
