package com.cp.stackunderflow.service;

import com.cp.stackunderflow.entity.User;
import com.cp.stackunderflow.exception.StackunderflowException;

import java.util.Optional;

public interface UserService {
    public String login(User user) throws StackunderflowException;
    public void logout(String token) throws StackunderflowException;
    public void addUser(User user) throws StackunderflowException;
    public Optional<User> getProfile(String token) throws StackunderflowException;
    public void updateProfile(User user, String token) throws StackunderflowException;
}
