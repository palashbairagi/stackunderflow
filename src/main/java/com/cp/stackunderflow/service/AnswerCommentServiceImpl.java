package com.cp.stackunderflow.service;

import com.cp.stackunderflow.entity.AnswerComment;
import com.cp.stackunderflow.entity.User;
import com.cp.stackunderflow.exception.StackunderflowException;
import com.cp.stackunderflow.repository.AnswerCommentRepository;
import com.cp.stackunderflow.security.TokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerCommentServiceImpl implements AnswerCommentService {

    private static final Logger logger = LoggerFactory.getLogger(AnswerCommentServiceImpl.class);

    @Autowired
    AnswerCommentRepository answerCommentRepository;

    @Autowired
    TokenHandler tokenHandler;

    @Override
    public void addComment(AnswerComment comment, String token) throws StackunderflowException {
        try {
            int id = tokenHandler.getIdFromToken(token);

            if (id == 0 || !tokenHandler.validate(token, id))
                throw new StackunderflowException(1001, "Unauthorized user");

            User user = new User();
            user.setId(id);

            comment.setUser(user);
            answerCommentRepository.save(comment);
        } catch (StackOverflowError se) {
            logger.error("AnswerCommentServiceImpl[addComment(AnswerComment, String)] : " + se);
            throw se;
        } catch (Exception e) {
            logger.error("AnswerCommentServiceImpl[addComment(AnswerComment, String)] : " + e);
            throw new StackunderflowException(1051, "Unable to add comment");
        }
    }

    @Override
    public void updateComment(AnswerComment comment, String token) throws StackunderflowException {
        try {
            int id = tokenHandler.getIdFromToken(token);

            Optional<AnswerComment> answerComment = answerCommentRepository.findById(comment.getId());
            if (answerComment == null)
                throw new StackunderflowException(1053, "Comment not found");

            if (id == 0 || !tokenHandler.validate(token, id) || answerComment.get().getUser().getId() != id)
                throw new StackunderflowException(1001, "Unauthorized user");

            User user = new User();
            user.setId(id);

            comment.setUser(user);
            answerCommentRepository.save(comment);

        } catch (StackOverflowError se) {
            logger.error("AnswerCommentServiceImpl[updateComment(AnswerComment, String)] : " + se);
            throw se;
        } catch (Exception e) {
            logger.error("AnswerCommentServiceImpl[updateComment(AnswerComment, String)] : " + e);
            throw new StackunderflowException(1052, "Unable to update comment");
        }
    }
}
