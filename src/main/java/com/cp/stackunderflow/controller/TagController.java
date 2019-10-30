package com.cp.stackunderflow.controller;

import com.cp.stackunderflow.entity.Tag;
import com.cp.stackunderflow.entity.User;
import com.cp.stackunderflow.exception.ErrorMessage;
import com.cp.stackunderflow.model.TagModel;
import com.cp.stackunderflow.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Api(description = "Perform operations realted to tag")
@RestController
@RequestMapping("${baseurl}")

public class TagController {

    private static final Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    TagService tagService;

    @ApiOperation(value = "Get tags")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "", response = Tag.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Request doesn't contain all the required fields", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @RequestMapping(value = "/tags", method = GET)
    public ResponseEntity getTags() {
        return new ResponseEntity( new TagModel(tagService.getTags()), HttpStatus.OK );
    }

}
