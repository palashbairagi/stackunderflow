package com.cp.stackunderflow.controller;

import com.cp.stackunderflow.model.TagModel;
import com.cp.stackunderflow.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("${baseurl}")

public class TagController {

    private static final Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    TagService tagService;

    @RequestMapping(value = "/tags", method = GET)
    public ResponseEntity getTags() {
        return new ResponseEntity( new TagModel(tagService.getTags()), HttpStatus.OK );
    }

}
