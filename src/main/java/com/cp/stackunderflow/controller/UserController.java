package com.cp.stackunderflow.controller;

import com.cp.stackunderflow.entity.LoginModel;
import com.cp.stackunderflow.entity.User;
import com.cp.stackunderflow.exception.ErrorMessage;
import com.cp.stackunderflow.service.UserService;
import io.swagger.annotations.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Api(description = "Perform operations realted to user")
@RestController
@RequestMapping("${baseurl}/user")

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @ApiOperation(value = "User Login")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "", responseHeaders = {@ResponseHeader(name = "token", response = String.class)}),
            @ApiResponse(code = 400, message = "Request doesn't contain all the required fields", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @RequestMapping(value = "/login", method = POST)
    public ResponseEntity login(@RequestBody LoginModel loginModel) {

        User user = new User();
        user.setEmail(loginModel.getEmail());
        user.setPassword(loginModel.getPassword());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("token", userService.login(user));
        return new ResponseEntity(responseHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "Logout user")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = ""),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @RequestMapping(value = "/logout", method = POST)
    public ResponseEntity logout(@RequestHeader(value="token") String token) {
        userService.logout(token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Request doesn't contain all the required fields", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = POST)
    public ResponseEntity register(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity(HttpStatus.CREATED );
    }

    @ApiOperation(value = "Get user profile")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "", response = User.class),
            @ApiResponse(code = 400, message = "Request doesn't contain all the required fields", response = ErrorMessage.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @RequestMapping(value = "/profile", method = GET)
    public ResponseEntity getProfile(@RequestHeader(value="token") String token) {
        return new ResponseEntity(userService.getProfile(token), HttpStatus.OK);
    }

    @ApiOperation(value = "Update user profile")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = ""),
            @ApiResponse(code = 400, message = "Request doesn't contain all the required fields", response = ErrorMessage.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Something went wrong on server", response = ErrorMessage.class)
    })
    @RequestMapping(value = "/profile", method = PUT)
    public ResponseEntity updateProfile(@RequestHeader(value="token") String token, @RequestBody User user) {
        userService.updateProfile(user, token);
        return new ResponseEntity(HttpStatus.OK);
    }

}
