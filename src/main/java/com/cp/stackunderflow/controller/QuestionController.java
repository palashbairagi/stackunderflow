package com.cp.stackunderflow.controller;

import com.cp.stackunderflow.entity.Question;
import com.cp.stackunderflow.exception.ErrorMessage;
import com.cp.stackunderflow.model.QuestionModel;
import com.cp.stackunderflow.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Api(description = "Perform operations realted to question")
@RestController
@RequestMapping("${baseurl}/question")

public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @ApiOperation(value = "Add a question")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Request doesn't contain all the required fields", response = ErrorMessage.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = POST)
    public ResponseEntity addQuestion(@RequestHeader(value="token") String token, @RequestBody Question question) {
        questionService.addQuestion(question, token);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update a question")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = ""),
            @ApiResponse(code = 400, message = "Request doesn't contain all the required fields", response = ErrorMessage.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @RequestMapping(method = PUT)
    public ResponseEntity updateQuestion(@RequestHeader(value="token") String token, @RequestBody Question question) {
        questionService.updateQuestion(question, token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Get list of recently posted questions")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "", response = QuestionModel.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Request doesn't contain all the required fields", response = ErrorMessage.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @RequestMapping(value = "/user/{userId}", method = GET)
    public ResponseEntity getQuestionsByUser(@RequestHeader(value="token") String token, @PathVariable("userId") Integer userId) {
        return new ResponseEntity(questionService.getQuestionsByUser(token, userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Get list of questions posted by a user")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "", response = QuestionModel.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Request doesn't contain all the required fields", response = ErrorMessage.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @RequestMapping(method = GET)
    public ResponseEntity getQuestions() {
        return new ResponseEntity(questionService.getQuestions(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get question by Id")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "", response = QuestionModel.class),
            @ApiResponse(code = 400, message = "Request doesn't contain all the required fields", response = ErrorMessage.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @RequestMapping(value = "/{questionId}", method = GET)
    public ResponseEntity getQuestion(@PathVariable("questionId") Integer questionId) {
        return new ResponseEntity(questionService.getQuestion(questionId), HttpStatus.OK);
    }

}
