package com.cp.stackunderflow.service;

import com.cp.stackunderflow.entity.*;
import com.cp.stackunderflow.exception.StackunderflowException;
import com.cp.stackunderflow.model.AnswerModel;
import com.cp.stackunderflow.model.CommentModel;
import com.cp.stackunderflow.model.QuestionModel;
import com.cp.stackunderflow.model.UserModel;
import com.cp.stackunderflow.repository.QuestionRepository;
import com.cp.stackunderflow.security.TokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TokenHandler tokenHandler;

    public void addQuestion(Question question, String token) throws StackunderflowException {
        try {
            int id = tokenHandler.getIdFromToken(token);

            if (id == 0 || !tokenHandler.validate(token, id))
                throw new StackunderflowException(1001, "Unauthorized user");

            User user = new User();
            user.setId(id);
            question.setUser(user);
            questionRepository.save(question);

        } catch (StackOverflowError se) {
            logger.error("QuestionServiceImpl[addQuestion(Question, String)] : " + se);
            throw se;
        } catch (Exception e) {
            logger.error("QuestionServiceImpl[addQuestion(Question, String)] : " + e);
            throw new StackunderflowException(1031, "Unable to add question");
        }
    }

    public void updateQuestion(Question question, String token) throws StackunderflowException {
        try {
            int id = tokenHandler.getIdFromToken(token);

            Optional<Question> question1 = questionRepository.findById(question.getId());
            if (question1 == null)
                throw new StackunderflowException(1034, "Question not found");

            if (id == 0 || !tokenHandler.validate(token, id) ||  question1.get().getUser().getId() != id)
                throw new StackunderflowException(1001, "Unauthorized user");

            User user = new User();
            user.setId(id);
            question.setUser(user);
            questionRepository.save(question);

        } catch (StackunderflowException se) {
            logger.error("QuestionServiceImpl[updateQuestion(Question, String)] : " + se);
            throw se;
        } catch (Exception e) {
            logger.error("QuestionServiceImpl[updateQuestion(Question, String)] : " + e);
            throw new StackunderflowException(1032, "Unable to update question");
        }
    }

    /*

    List user specific questions

     */
    public List<QuestionModel> getQuestionsByUser(String token, Integer userId) throws StackunderflowException {
        try {
            int id = tokenHandler.getIdFromToken(token);

            if (id == 0 || !tokenHandler.validate(token, id))
                throw new StackunderflowException(1001, "Unauthorized user");

            User user = new User();
            user.setId(userId);

            List<Question> questions = questionRepository.findByUser(user);

            return getModifiedResponse(questions);

        } catch (StackunderflowException se) {
            logger.error("QuestionServiceImpl[getQuestionsByUser(String, Integer)] : " + se);
            throw se;
        } catch (Exception e) {
            logger.error("QuestionServiceImpl[getQuestionsByUser(String, Integer)] : " + e);
            throw new StackunderflowException(1033, "Unable to get questions");
        }
    }

    /*

    List recently posted questions

     */
    public List<QuestionModel> getQuestions() throws StackunderflowException {
        try {
            List<Question> questions = questionRepository.findAll();
            return getModifiedResponse(questions);
        } catch (Exception e) {
            logger.error("QuestionServiceImpl[getQuestions()] : " + e);
            throw new StackunderflowException(1033, "Unable to get questions");
        }
    }

    public QuestionModel getQuestion(Integer questionId) throws StackunderflowException {
        Question question;
        try {
             Optional<Question> questionOptional = questionRepository.findById(questionId);
             if(questionOptional.isPresent())
                 question = questionOptional.get();
             else
                 throw new StackunderflowException(1034, "Question not found");

            return getQuestionModel(question);
        } catch (StackunderflowException se) {
            logger.error("QuestionServiceImpl[getQuestion(Integer)] : " + se);
            throw se;
        }catch (Exception e) {
            logger.error("QuestionServiceImpl[getQuestion(Integer)] : " + e);
            throw new StackunderflowException(1035, "Unable to get question");
        }
    }

    private List<QuestionModel> getModifiedResponse(List<Question> questions) {

        List<QuestionModel> questionModel = new ArrayList<QuestionModel>();

        for (Question question : questions) {
            questionModel.add(getQuestionModel(question));
        }

        return questionModel;
    }

    private QuestionModel getQuestionModel(Question question) {

        QuestionModel questionModel = new QuestionModel();
        questionModel.setId(question.getId());
        questionModel.setTitle(question.getTitle());
        questionModel.setBody(question.getBody());
        questionModel.setLastUpdate(question.getLastUpdate());

        UserModel userModel = new UserModel();
        userModel.setUserId(question.getUser().getId());
        userModel.setUserName(question.getUser().getDisplayName());

        questionModel.setUser(userModel);
        questionModel.setTags(question.getTags());

        List<CommentModel> commentModels = new ArrayList<>();
        List<QuestionComment> comments = question.getQuestionComment();

        for (QuestionComment questionComment: comments) {
            CommentModel commentModel = new CommentModel();
            commentModel.setId(questionComment.getId());
            commentModel.setBody(questionComment.getBody());
            commentModel.setLastUpdate(questionComment.getLastUpdate());

            UserModel userModel1 = new UserModel();
            userModel1.setUserId(questionComment.getUser().getId());
            userModel1.setUserName(questionComment.getUser().getDisplayName());
            commentModel.setUser(userModel1);

            commentModels.add(commentModel);
        }

        questionModel.setComments(commentModels);

        List<Answer> answers = question.getAnswers();
        List<AnswerModel> answerModels = new ArrayList<>();

        for (Answer answer:answers) {
            AnswerModel answerModel = new AnswerModel();
            answerModel.setId(answer.getId());
            answerModel.setBody(answer.getBody());
            answerModel.setLastUpdate(answer.getLastUpdate());

            UserModel userModel1 = new UserModel();
            userModel1.setUserId(answer.getUser().getId());
            userModel1.setUserName(answer.getUser().getDisplayName());

            answerModel.setUser(userModel1);

            List<AnswerComment> answerComments = answer.getComments();
            List<CommentModel> commentModels1 = new ArrayList<>();

            for (AnswerComment answerComment: answerComments) {
                CommentModel commentModel = new CommentModel();
                commentModel.setId(answerComment.getId());
                commentModel.setBody(answerComment.getBody());
                commentModel.setLastUpdate(answerComment.getLastUpdate());

                UserModel userModel2 = new UserModel();
                userModel2.setUserId(answerComment.getUser().getId());
                userModel2.setUserName(answerComment.getUser().getDisplayName());
                commentModel.setUser(userModel2);

                commentModels1.add(commentModel);
            }

            answerModel.setComments(commentModels1);
            answerModels.add(answerModel);
        }

        questionModel.setAnswers(answerModels);

        return questionModel;
    }
}

