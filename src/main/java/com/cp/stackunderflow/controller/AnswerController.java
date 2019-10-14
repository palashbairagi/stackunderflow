package com.cp.stackunderflow.controller;

import com.cp.stackunderflow.entity.Answer;
import com.cp.stackunderflow.service.AnswerService;
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
public class AnswerController {

    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private AnswerService answerService;

    @RequestMapping(method = POST)
    public ResponseEntity addAnswer(@RequestHeader(value="token") String token, @RequestBody Answer answer) {
        answerService.addAnswer(answer, token);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = PUT)
    public ResponseEntity updateAnswer(@RequestHeader(value="token") String token, @RequestBody Answer answer) {
        answerService.updateAnswer(answer, token);
        return new ResponseEntity(HttpStatus.OK);
    }
}
