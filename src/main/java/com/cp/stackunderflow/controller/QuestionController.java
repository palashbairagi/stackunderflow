package com.cp.stackunderflow.controller;

import com.cp.stackunderflow.entity.Question;
import com.cp.stackunderflow.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("${baseurl}/question")

public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @RequestMapping(method = POST)
    public ResponseEntity addQuestion(@RequestHeader(value="token") String token, @RequestBody Question question) {
        questionService.addQuestion(question, token);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = PUT)
    public ResponseEntity updateQuestion(@RequestHeader(value="token") String token, @RequestBody Question question) {
        questionService.updateQuestion(question, token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userId}", method = GET)
    public ResponseEntity getQuestionsByUser(@RequestHeader(value="token") String token, @PathVariable("userId") Integer userId) {
        return new ResponseEntity(questionService.getQuestionsByUser(token, userId), HttpStatus.OK);
    }

    @RequestMapping(method = GET)
    public ResponseEntity getQuestions() {
        return new ResponseEntity(questionService.getQuestions(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{questionId}", method = GET)
    public ResponseEntity getQuestion(@PathVariable("questionId") Integer questionId) {
        return new ResponseEntity(questionService.getQuestion(questionId), HttpStatus.OK);
    }

}
