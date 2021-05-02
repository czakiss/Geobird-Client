package com.example.geobirdclient.models.UserData;

public class UserLoginResponse {
    private UserData user;
    private String resultMessage;
    private UserService.Login_Status loginStatus;

    public UserLoginResponse(UserData user, String resultMessage, UserService.Login_Status loginStatus) {
        this.user = user;
        this.resultMessage = resultMessage;
        this.loginStatus = loginStatus;
    }

    public UserLoginResponse() {
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public UserService.Login_Status getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(UserService.Login_Status loginStatus) {
        this.loginStatus = loginStatus;
    }
}
