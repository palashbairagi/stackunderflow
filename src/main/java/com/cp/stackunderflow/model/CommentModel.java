package com.cp.stackunderflow.model;


import java.time.LocalDateTime;

public class CommentModel {

    private Integer id;
    private String body;
    private LocalDateTime lastUpdate;
    private UserModel user;

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

}
