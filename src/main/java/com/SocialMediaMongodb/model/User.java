package com.SocialMediaMongodb.model;

import org.springframework.data.annotation.Id;

public class User {
    @Id
    public String id;
    public String userName;
    public String password;

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
