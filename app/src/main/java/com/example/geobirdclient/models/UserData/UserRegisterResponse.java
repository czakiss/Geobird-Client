package com.example.geobirdclient.models.UserData;

public class UserRegisterResponse {
    private UserData user;
    private String resultMessage;
   // private UserService.Registration_Status registrationStatus;

  /*  public UserRegisterResponse(UserData user, String resultMessage, UserService.Registration_Status registrationStatus) {
        this.user = user;
        this.resultMessage = resultMessage;
        this.registrationStatus = registrationStatus;
    }*/

    public UserRegisterResponse() {
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

   /* public UserService.Registration_Status getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(UserService.Registration_Status registrationStatus) {
        this.registrationStatus = registrationStatus;
    }*/
}
