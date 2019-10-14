package com.cp.stackunderflow.controller;

import com.cp.stackunderflow.entity.AnswerComment;
import com.cp.stackunderflow.service.AnswerCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("${baseurl}/answer")
public class AnswerCommentController {

    private static final Logger logger = LoggerFactory.getLogger(AnswerCommentController.class);

    @Autowired
    AnswerCommentService answerCommentService;

    @RequestMapping(value = "/comment", method = POST)
    public ResponseEntity addComment(@RequestHeader(value="token") String token, @RequestBody AnswerComment comment) {
            answerCommentService.addComment(comment, token);
            return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/comment", method = PUT)
    public ResponseEntity updateComment(@RequestHeader(value="token") String token, @RequestBody AnswerComment comment) {
            answerCommentService.updateComment(comment, token);
            return new ResponseEntity(HttpStatus.OK);
    }

}
