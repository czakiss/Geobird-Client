package com.example.geobirdclient.models.UserData;

public class UserDeleteResponse {
    private UserData user;
    private String resultMessage;
   // private UserService.Delete_Status deleteStatus;

  /*  public UserDeleteResponse(UserData user, String resultMessage, UserService.Delete_Status deleteStatus) {
        this.user = user;
        this.resultMessage = resultMessage;
        this.deleteStatus = deleteStatus;
    }*/
    public UserDeleteResponse(){

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
/*
    public UserService.Delete_Status getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(UserService.Delete_Status deleteStatus) {
        this.deleteStatus = deleteStatus;
    }*/
}
