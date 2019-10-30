package com.cp.stackunderflow.controller;

import com.cp.stackunderflow.entity.QuestionComment;
import com.cp.stackunderflow.exception.ErrorMessage;
import com.cp.stackunderflow.service.QuestionCommentService;
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

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


@Api(description = "Perform operations realted to comment on question")
@RestController
@RequestMapping("${baseurl}/question")
public class QuestionCommentController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionCommentController.class);

    @Autowired
    private QuestionCommentService commentService;

    @ApiOperation(value = "Add comment to a question")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Request doesn't contain all the required fields", response = ErrorMessage.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/comment", method = POST)
    public ResponseEntity addComment(@RequestHeader(value="token") String token, @RequestBody QuestionComment comment) {
        commentService.addComment(comment, token);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update comment to a question")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = ""),
            @ApiResponse(code = 400, message = "Request doesn't contain all the required fields", response = ErrorMessage.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @RequestMapping(value = "/comment", method = PUT)
    public ResponseEntity updateComment(@RequestHeader(value="token") String token, @RequestBody QuestionComment comment) {
        commentService.updateComment(comment, token);
        return new ResponseEntity(HttpStatus.OK);
    }

}
