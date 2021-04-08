package com.rtsproject.ryvel_2018939060053_final;

import android.app.Application;

public class User extends Application {
    private String username;
    private String userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
