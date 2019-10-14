package com.cp.stackunderflow.service;

import com.cp.stackunderflow.entity.Tag;
import com.cp.stackunderflow.exception.StackunderflowException;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface TagService {
    public Set<Tag> getTags() throws StackunderflowException;
}
