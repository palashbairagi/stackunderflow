package com.cp.stackunderflow.service;

import com.cp.stackunderflow.controller.TagController;
import com.cp.stackunderflow.entity.Tag;
import com.cp.stackunderflow.exception.StackunderflowException;
import com.cp.stackunderflow.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService{

    private static final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

    @Autowired
    private TagRepository tagRepository;

    public Set<Tag> getTags() throws StackunderflowException {
        try {
            Set<Tag> tags  = new HashSet<Tag>(tagRepository.findAll());
            return tags;
        }catch (Exception e) {
            logger.error("TagServiceImpl[getTags()] : " + e);
            throw new StackunderflowException(1013, "Unable to get tags");
        }
    }
}
