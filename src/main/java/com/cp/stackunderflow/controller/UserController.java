package com.cp.stackunderflow.controller;

import com.cp.stackunderflow.entity.User;
import com.cp.stackunderflow.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("${baseurl}/user")

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = POST)
    public ResponseEntity login(@RequestBody User user) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("token", userService.login(user));
        return new ResponseEntity(responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = POST)
    public ResponseEntity logout(@RequestHeader(value="token") String token) {
        userService.logout(token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = POST)
    public ResponseEntity register(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity(HttpStatus.CREATED );
    }

    @RequestMapping(value = "/profile", method = GET)
    public ResponseEntity getProfile(@RequestHeader(value="token") String token) {
        return new ResponseEntity(userService.getProfile(token), HttpStatus.OK);
    }

    @RequestMapping(value = "/profile", method = PUT)
    public ResponseEntity updateProfile(@RequestHeader(value="token") String token, @RequestBody User user) {
        userService.updateProfile(user, token);
        return new ResponseEntity(HttpStatus.OK);
    }

}
