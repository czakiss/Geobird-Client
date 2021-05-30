package com.example.geobirdclient.http;

import com.example.geobirdclient.models.User.User;

public interface UserLoginCallback {
    void redirectToMain(User user);
    void informAboutFailedLogin();
    void saveUserCredentials(String login, String password);
}
