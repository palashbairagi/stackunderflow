package com.cp.stackunderflow.security;

import com.cp.stackunderflow.controller.AnswerCommentController;
import com.cp.stackunderflow.exception.StackunderflowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidateUserInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ValidateUserInterceptor.class);

    @Autowired
    TokenHandler tokenHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            if (!tokenHandler.validate(request.getHeader("token")))
                throw new StackunderflowException(1001, "Unauthorized user");
            return true;
        } catch (StackunderflowException se) {
            throw se;
        } catch (Exception e) {
            logger.error("ValidateUserInterceptor[preHandle(HttpServletRequest, HttpServletResponse, Object)] : " + e);
            throw e;
        }
    }
}
