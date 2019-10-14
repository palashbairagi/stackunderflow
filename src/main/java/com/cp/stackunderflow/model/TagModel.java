package com.cp.stackunderflow.model;

import com.cp.stackunderflow.entity.Tag;

import java.util.Set;

public class TagModel {

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    private Set<Tag> tags;

    public TagModel(Set<Tag> tags) {
        this.tags = tags;
    }
}
