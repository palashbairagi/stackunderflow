package com.cp.stackunderflow.service;

import com.cp.stackunderflow.entity.Answer;
import com.cp.stackunderflow.entity.User;
import com.cp.stackunderflow.exception.StackunderflowException;
import com.cp.stackunderflow.repository.AnswerRepository;
import com.cp.stackunderflow.security.TokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {

    private static final Logger logger = LoggerFactory.getLogger(AnswerServiceImpl.class);

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    TokenHandler tokenHandler;

    @Override
    public void addAnswer(Answer answer, String token) throws StackunderflowException {
        try {
            int id = tokenHandler.getIdFromToken(token);

            if (id == 0 || !tokenHandler.validate(token, id))
                throw new StackunderflowException(1001, "Unauthorized user");

            User user = new User();
            user.setId(id);

            answer.setUser(user);
            answerRepository.save(answer);

        } catch (StackOverflowError se) {
            logger.error("AnswerServiceImpl[addAnswer(Answer, String)] : " + se);
            throw se;
        } catch (Exception e) {
            logger.error("AnswerServiceImpl[addAnswer(Answer, String)] : " + e);
            throw new StackunderflowException(1041, "Unable to add answer");
        }
    }

    @Override
    public void updateAnswer(Answer answer, String token) throws StackunderflowException {
        try {
            int id = tokenHandler.getIdFromToken(token);

            Optional<Answer> answer1 = answerRepository.findById(answer.getId());
            if (answer1 == null)
                throw new StackunderflowException(1043, "Answer not found");

            if (id == 0 || !tokenHandler.validate(token, id) || answer1.get().getUser().getId() != id)
                throw new StackunderflowException(1001, "Unauthorized user");


            User user = new User();
            user.setId(id);

            answer.setUser(user);
            answerRepository.save(answer);

        } catch (StackunderflowException se) {
            logger.error("AnswerServiceImpl[updateAnswer(Answer, String)] : " + se);
            throw se;
        } catch (Exception e) {
            logger.error("AnswerServiceImpl[updateAnswer(Answer, String)] : " + e);
            throw new StackunderflowException(1042, "Unable to update answer");
        }
    }
}
