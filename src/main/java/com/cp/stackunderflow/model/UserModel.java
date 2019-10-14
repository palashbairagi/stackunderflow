package com.cp.stackunderflow.model;

public class UserModel {

    private Integer id;
    private String displayName;

    public Integer getUserId() {
        return id;
    }

    public void setUserId(Integer userId) {
        this.id = userId;
    }

    public String getUserName() {
        return displayName;
    }

    public void setUserName(String userName) {
        this.displayName = userName;
    }
}
