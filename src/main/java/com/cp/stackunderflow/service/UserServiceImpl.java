package com.cp.stackunderflow.service;

import com.cp.stackunderflow.entity.User;
import com.cp.stackunderflow.exception.StackunderflowException;
import com.cp.stackunderflow.repository.UserRepository;
import com.cp.stackunderflow.security.TokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    TokenHandler tokenHandler;

    @Autowired
    UserRepository userRepository;

    @Override
    public void addUser(User user) throws StackunderflowException {
        try {

            if (userRepository.findByEmail(user.getEmail()) != null)
                throw new StackunderflowException(1021, "User already exists");

            user.setPassword(toHexString(getSHA(user.getPassword())));
            userRepository.save(user);

        } catch (StackunderflowException se) {
            logger.error("UserServiceImpl[addUser(User)] :" + se);
            throw se;
        } catch (Exception e) {
            logger.error("UserServiceImpl[addUser(User)] :" + e);
            throw new StackunderflowException(1022, "Unable to register");
        }
    }

    @Override
    public String login(User user) throws StackunderflowException {
        try {
            User user1 = userRepository.findByEmail(user.getEmail());

            if (user1 == null || !toHexString(getSHA(user.getPassword())).equals(user1.getPassword()))
                throw new StackunderflowException(1024, "Invalid credentials");

            return tokenHandler.createToken(user1.getId());
        } catch (StackunderflowException se) {
            logger.error("UserServiceImpl[login(User)] :" + se);
            throw se;
        } catch (Exception e) {
            logger.error("UserServiceImpl[login(User)] :" + e);
            throw new StackunderflowException(1023, "Unable to login");
        }
    }

    @Override
    public void logout(String token) {
        try {
            tokenHandler.revokeToken(token);
        } catch (Exception e) {
            logger.error("UserServiceImpl[logout(String)] :" + e);
        }
    }

    @Override
    public Optional<User> getProfile(String token) throws StackunderflowException {
        Optional<User> user;
        try {
            int id = tokenHandler.getIdFromToken(token);

            if (id == 0 || !tokenHandler.validate(token, id))
                throw new StackunderflowException(1001, "Unauthorized user");

            user = userRepository.findById(id);
            user.get().setPassword("");

        } catch (StackunderflowException se) {
            logger.error("UserServiceImpl[getProfile(String)] :" + se);
            throw se;
        } catch (Exception e) {
            logger.error("UserServiceImpl[getProfile(String)] :" + e);
            throw new StackunderflowException(1025, "Unable to get profile");
        }
        return user;
    }

    @Override
    public void updateProfile(User user, String token) throws StackunderflowException {

        try {

            int id = tokenHandler.getIdFromToken(token);

            if (id == 0 || !tokenHandler.validate(token, id) || id != user.getId())
                throw new StackunderflowException(1001, "Unauthorized user");

            if (user.getPassword().equals("")) {
                Optional <User> oldUser = userRepository.findById(id);
                user.setPassword(oldUser.get().getPassword());
            } else {
                user.setPassword(toHexString(getSHA(user.getPassword())));
            }

            userRepository.save(user);

        } catch (StackunderflowException se) {
            logger.error("UserServiceImpl[updateProfile(User, String)] : " + se);
            throw se;
        } catch (Exception e) {
            logger.error("UserServiceImpl[updateProfile(User, String)] : " + e);
            throw new StackunderflowException(1026, "Unable to update profile");
        }
    }

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
}
